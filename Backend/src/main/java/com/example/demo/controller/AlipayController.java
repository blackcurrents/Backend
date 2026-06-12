package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.context.UserContext;
import com.example.demo.entity.Fine;
import com.example.demo.service.AlipayService;
import com.example.demo.service.FineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/alipay")
@RequiredArgsConstructor
@CrossOrigin
@Tag(name = "支付宝支付", description = "支付宝沙箱支付相关接口")
public class AlipayController {

    private final AlipayService alipayService;
    private final FineService fineService;

    @PostMapping("/create")
    @Operation(summary = "创建罚款支付订单", description = "返回支付宝收银台跳转URL")
    public ResponseEntity<Result<Map<String, Object>>> createPayment(
            @RequestParam Long fineId,
            @RequestParam(required = false) String source) {
        Fine fine = fineService.getById(fineId);

        if (fine == null) {
            return ResponseEntity.ok(Result.error(400, "罚款记录不存在"));
        }
        // 非管理员只能给自己缴费
        if (!UserContext.isAdmin() && !fine.getUserId().equals(UserContext.getCurrentUserId())) {
            return ResponseEntity.ok(Result.error(400, "罚款记录不存在"));
        }
        if (fine.getStatus() == 1) {
            return ResponseEntity.ok(Result.error(400, "该罚款已支付"));
        }

        try {
            String payUrl = alipayService.createPayUrl(fineId, fine.getAmount(), source);
            Map<String, Object> data = new HashMap<>();
            data.put("payUrl", payUrl);
            data.put("fineId", fineId);
            data.put("amount", fine.getAmount());
            return ResponseEntity.ok(Result.success(data));
        } catch (Exception e) {
            log.error("创建支付宝订单失败", e);
            return ResponseEntity.ok(Result.error(500, "创建支付订单失败：" + e.getMessage()));
        }
    }

    @PostMapping("/notify")
    @Operation(summary = "支付宝异步通知", description = "支付宝服务器回调，验证签名并更新罚款状态")
    public String notify(HttpServletRequest request) {
        Map<String, String> params = new HashMap<>();
        Iterator<String> iter = request.getParameterNames().asIterator();
        while (iter.hasNext()) {
            String name = iter.next();
            String[] values = request.getParameterValues(name);
            if (values != null && values.length > 0) {
                params.put(name, values[0]);
            }
        }

        log.info("收到支付宝异步通知: {}", params);

        if (!alipayService.verifyNotify(params)) {
            log.warn("支付宝通知验签失败");
            return "failure";
        }

        String tradeStatus = params.get("trade_status");
        if ("TRADE_SUCCESS".equals(tradeStatus) || "TRADE_FINISHED".equals(tradeStatus)) {
            String outTradeNo = params.get("out_trade_no");
            try {
                // 从交易号中解析罚款ID: FINE-{fineId}-{timestamp}
                String[] parts = outTradeNo.split("-");
                Long fineId = Long.parseLong(parts[1]);
                String totalAmount = params.get("total_amount");
                log.info("支付宝回调：罚款ID={}, 交易号={}, 金额={}", fineId, outTradeNo, totalAmount);
                fineService.markFinePaidByAlipay(fineId, outTradeNo, totalAmount);
            } catch (Exception e) {
                log.error("处理支付宝通知异常，交易号: {}", outTradeNo, e);
                return "failure";
            }
        }

        return "success";
    }

    @GetMapping("/status/{fineId}")
    @Operation(summary = "查询罚款支付状态", description = "前端轮询，确认支付是否已完成")
    public ResponseEntity<Result<Map<String, Object>>> checkStatus(@PathVariable Long fineId) {
        Fine fine = fineService.getById(fineId);
        Map<String, Object> data = new HashMap<>();
        if (fine == null) {
            return ResponseEntity.ok(Result.error(404, "罚款记录不存在"));
        }
        data.put("paid", fine.getStatus() == 1);
        data.put("fineId", fine.getId());
        data.put("paidAmount", fine.getPaidAmount());
        data.put("payTime", fine.getPayTime());
        return ResponseEntity.ok(Result.success(data));
    }

    @PostMapping("/confirm")
    @Operation(summary = "同步返回确认", description = "用户从支付宝跳回后，携带交易信息确认支付结果")
    public ResponseEntity<Result<Map<String, Object>>> confirmReturn(
            @RequestParam Long fineId,
            @RequestParam String outTradeNo,
            @RequestParam(required = false) String totalAmount) {
        Fine fine = alipayService.confirmReturn(fineId, outTradeNo, totalAmount);
        if (fine == null) {
            return ResponseEntity.ok(Result.error(400, "确认失败"));
        }
        Map<String, Object> data = new HashMap<>();
        data.put("confirmed", true);
        data.put("fineId", fine.getId());
        data.put("paidAmount", fine.getPaidAmount());
        data.put("payTime", fine.getPayTime());
        return ResponseEntity.ok(Result.success(data));
    }
}
