package com.example.demo.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.example.demo.config.AlipayConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AlipayService {

    private final AlipayClient alipayClient;
    private final AlipayConfig alipayConfig;
    private final FineService fineService;

    /**
     * 创建支付链接，返回支付宝收银台跳转URL
     */
    public String createPayUrl(Long fineId, java.math.BigDecimal amount, String source) throws AlipayApiException {
        String tradeNo = "FINE-" + fineId + "-" + System.currentTimeMillis();
        log.info("创建支付宝订单: tradeNo={}, amount={}, source={}", tradeNo, amount, source);

        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setNotifyUrl(alipayConfig.getNotifyUrl());
        String returnUrl = alipayConfig.getReturnUrl() + "?fineId=" + fineId;
        if (source != null && !source.isEmpty()) {
            returnUrl += "&source=" + source;
        }
        request.setReturnUrl(returnUrl);

        AlipayTradePagePayModel model = new AlipayTradePagePayModel();
        model.setOutTradeNo(tradeNo);
        model.setTotalAmount(amount.toPlainString());
        model.setSubject("图书馆罚款缴纳");
        model.setProductCode("FAST_INSTANT_TRADE_PAY");
        request.setBizModel(model);

        // 用 sdkExecute 获取签名参数，再拼接完整URL
        AlipayTradePagePayResponse response = alipayClient.sdkExecute(request);
        log.info("支付宝响应: isSuccess={}, code={}, subCode={}, subMsg={}",
                response.isSuccess(), response.getCode(), response.getSubCode(), response.getSubMsg());

        String url = alipayConfig.getGatewayUrl() + "?" + response.getBody();
        log.info("支付宝跳转URL: {}", url);
        return url;
    }

    /**
     * 验证支付宝异步通知签名
     */
    public boolean verifyNotify(Map<String, String> params) {
        return alipayConfig.verifySign(params);
    }

    /**
     * 同步返回确认：用户从支付宝跳回后，前端调用此接口确认支付结果
     * 作为异步通知的兜底方案，返回确认后的罚款记录
     */
    public com.example.demo.entity.Fine confirmReturn(Long fineId, String outTradeNo, String totalAmount) {
        com.example.demo.entity.Fine fine = fineService.getById(fineId);
        if (fine == null) {
            log.warn("确认回调：罚款记录不存在, fineId={}", fineId);
            return null;
        }
        if (fine.getStatus() == 1) {
            log.info("确认回调：罚款已支付（幂等）, fineId={}", fineId);
            return fine;
        }
        // 校验交易号格式：FINE-{fineId}-{timestamp}
        if (outTradeNo == null || !outTradeNo.startsWith("FINE-" + fineId + "-")) {
            log.warn("确认回调：交易号不匹配, fineId={}, outTradeNo={}", fineId, outTradeNo);
            return null;
        }
        log.info("确认回调：标记罚款已支付, fineId={}, outTradeNo={}, amount={}", fineId, outTradeNo, totalAmount);
        fineService.markFinePaidByAlipay(fineId, outTradeNo, totalAmount);
        return fineService.getById(fineId);
    }
}
