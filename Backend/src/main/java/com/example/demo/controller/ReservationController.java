package com.example.demo.controller;

import com.example.demo.annotation.RequireRole;
import com.example.demo.common.Result;
import com.example.demo.context.UserContext;
import com.example.demo.entity.Reservation;
import com.example.demo.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 预约控制器
 */
@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
@CrossOrigin
@Tag(name = "预约管理", description = "图书预约相关接口")
public class ReservationController {

    private final ReservationService reservationService;

    /**
     * 查询我的预约列表（普通用户）
     * 查询指定用户的预约列表（管理员）
     */
    @GetMapping
    @Operation(summary = "查询预约列表", description = "普通用户查自己的，管理员可查任意用户")
    public ResponseEntity<Result<List<Reservation>>> getUserReservations(
            @RequestParam(required = false) Long userId) {

        boolean isAdmin = UserContext.isAdmin();
        Long currentUserId = UserContext.getCurrentUserId();

        // 如果没有传userId，默认查自己的
        if (userId == null) {
            userId = currentUserId;
        }

        // 权限校验：不是管理员且不是查自己
        if (!isAdmin && !currentUserId.equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Result.error(403, "无权限查看他人预约记录"));
        }

        List<Reservation> reservations = reservationService.getUserReservations(userId);
        return ResponseEntity.ok(Result.success(reservations));
    }

    /**
     * 取消预约
     */
    @DeleteMapping("/{reservationId}")
    @Operation(summary = "取消预约")
    public ResponseEntity<Result<String>> cancelReservation(@PathVariable Long reservationId) {
        try {
            reservationService.cancelReservation(reservationId);
            return ResponseEntity.ok(Result.success("取消预约成功", null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Result.error(400, e.getMessage()));
        }
    }

    /**
     * 管理员：查询所有等待中的预约
     */
    @GetMapping("/waiting")
    @RequireRole(1)
    @Operation(summary = "查询所有等待中的预约", description = "管理员专用")
    public ResponseEntity<Result<List<Reservation>>> getAllWaitingReservations() {
        List<Reservation> reservations = reservationService.getAllWaitingReservations();
        return ResponseEntity.ok(Result.success(reservations));
    }

    /**
     * 管理员：根据图书ID查询预约队列
     */
    @GetMapping("/book/{bookId}")
    @RequireRole(1)
    @Operation(summary = "根据图书ID查询预约队列", description = "管理员专用")
    public ResponseEntity<Result<List<Reservation>>> getReservationsByBook(@PathVariable Long bookId) {
        List<Reservation> reservations = reservationService.getReservationsByBook(bookId);
        return ResponseEntity.ok(Result.success(reservations));
    }

    /**
     * 管理员：通知预约用户取书
     */
    @PostMapping("/{reservationId}/notify")
    @RequireRole(1)
    @Operation(summary = "通知预约用户取书", description = "管理员专用")
    public ResponseEntity<Result<String>> notifyUser(@PathVariable Long reservationId) {
        try {
            reservationService.notifyUser(reservationId);
            return ResponseEntity.ok(Result.success("通知成功", null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Result.error(400, e.getMessage()));
        }
    }
}