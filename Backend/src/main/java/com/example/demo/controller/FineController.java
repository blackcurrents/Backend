package com.example.demo.controller;

import com.example.demo.annotation.RequireRole;
import com.example.demo.common.Result;
import com.example.demo.context.UserContext;
import com.example.demo.entity.Fine;
import com.example.demo.service.FineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * 罚款/账单控制器
 */
@RestController
@RequestMapping("/fines")
@RequiredArgsConstructor
@CrossOrigin
@Tag(name = "罚款管理", description = "账单和罚款相关接口")
public class FineController {

    private final FineService fineService;

    /**
     * 查询我的未支付罚款总额
     */
    @GetMapping("/my/total")
    @Operation(summary = "查询我的未支付罚款总额")
    public ResponseEntity<Result<BigDecimal>> getMyUnpaidTotal() {
        Long currentUserId = UserContext.getCurrentUserId();
        BigDecimal total = fineService.getUnpaidFineByUser(currentUserId);
        return ResponseEntity.ok(Result.success(total));
    }

    /**
     * 查询我的未支付罚款列表
     */
    @GetMapping("/my/unpaid")
    @Operation(summary = "查询我的未支付罚款列表")
    public ResponseEntity<Result<List<Fine>>> getMyUnpaidFines() {
        Long currentUserId = UserContext.getCurrentUserId();
        List<Fine> fines = fineService.getUserUnpaidFines(currentUserId);
        return ResponseEntity.ok(Result.success(fines));
    }

    /**
     * 查询指定用户的未支付罚款总额（管理员）
     */
    @GetMapping("/user/{userId}/total")
    @RequireRole(1)
    @Operation(summary = "查询指定用户未支付罚款总额", description = "管理员专用")
    public ResponseEntity<Result<BigDecimal>> getUserUnpaidTotal(@PathVariable Long userId) {
        BigDecimal total = fineService.getUnpaidFineByUser(userId);
        return ResponseEntity.ok(Result.success(total));
    }

    /**
     * 查询指定用户的未支付罚款列表（管理员）
     */
    @GetMapping("/user/{userId}/unpaid")
    @RequireRole(1)
    @Operation(summary = "查询指定用户未支付罚款列表", description = "管理员专用")
    public ResponseEntity<Result<List<Fine>>> getUserUnpaidFines(@PathVariable Long userId) {
        List<Fine> fines = fineService.getUserUnpaidFines(userId);
        return ResponseEntity.ok(Result.success(fines));
    }

    /**
     * 支付单笔罚款
     */
    @PostMapping("/{fineId}/pay")
    @Operation(summary = "支付单笔罚款")
    public ResponseEntity<Result<Map<String, Object>>> payFine(@PathVariable Long fineId) {
        try {
            Fine fine = fineService.payFine(fineId);
            Map<String, Object> data = new HashMap<>();
            data.put("fineId", fine.getId());
            data.put("paidAmount", fine.getPaidAmount());
            data.put("message", "支付成功");
            return ResponseEntity.ok(Result.success(data));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Result.error(400, e.getMessage()));
        }
    }

    /**
     * 支付我的所有罚款
     */
    @PostMapping("/my/pay-all")
    @Operation(summary = "支付我的所有罚款")
    public ResponseEntity<Result<Map<String, Object>>> payAllMyFines() {
        Long currentUserId = UserContext.getCurrentUserId();
        try {
            BigDecimal totalAmount = fineService.payAllFines(currentUserId);
            Map<String, Object> data = new HashMap<>();
            data.put("totalAmount", totalAmount);
            data.put("message", "已支付全部罚款 " + totalAmount + " 元");
            return ResponseEntity.ok(Result.success(data));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Result.error(400, e.getMessage()));
        }
    }

    /**
     * 管理员：支付指定用户的所有罚款
     */
    @PostMapping("/user/{userId}/pay-all")
    @RequireRole(1)
    @Operation(summary = "管理员代缴用户罚款", description = "管理员专用")
    public ResponseEntity<Result<Map<String, Object>>> payAllFinesForUser(@PathVariable Long userId) {
        try {
            BigDecimal totalAmount = fineService.payAllFines(userId);
            Map<String, Object> data = new HashMap<>();
            data.put("userId", userId);
            data.put("totalAmount", totalAmount);
            data.put("message", "已代缴用户全部罚款 " + totalAmount + " 元");
            return ResponseEntity.ok(Result.success(data));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Result.error(400, e.getMessage()));
        }
    }

    /**
     * 管理员：查询所有未支付罚款（全局）
     */
    @GetMapping("/admin/all-unpaid")
    @RequireRole(1)
    @Operation(summary = "查询所有未支付罚款", description = "管理员专用")
    public ResponseEntity<Result<List<Fine>>> getAllUnpaidFines() {
        List<Fine> fines = fineService.getAllUnpaidFines();
        return ResponseEntity.ok(Result.success(fines));
    }

    /**
     * 管理员：查询罚款统计
     */
    @GetMapping("/admin/statistics")
    @RequireRole(1)
    @Operation(summary = "查询罚款统计", description = "管理员专用")
    public ResponseEntity<Result<Map<String, Object>>> getFineStatistics() {
        Map<String, Object> statistics = fineService.getFineStatistics();
        return ResponseEntity.ok(Result.success(statistics));
    }
}