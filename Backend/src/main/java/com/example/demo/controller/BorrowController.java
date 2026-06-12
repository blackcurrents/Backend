package com.example.demo.controller;

import com.example.demo.annotation.RequireRole;
import com.example.demo.common.Result;
import com.example.demo.context.UserContext;
import com.example.demo.service.BorrowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/borrow")
@RequiredArgsConstructor
@CrossOrigin
@Tag(name = "借阅管理", description = "图书借阅相关接口")
public class BorrowController {

    private final BorrowService borrowService;

    @PostMapping("/borrow")
    @Operation(summary = "借书")
    public ResponseEntity<Result<String>> borrowBook(@RequestParam Long userId, @RequestParam Long bookId) {
        try {
            boolean isAdmin = UserContext.isAdmin();
            if (isAdmin) {
                borrowService.adminBorrowBook(userId, bookId);
            } else {
                Long currentUserId = UserContext.getCurrentUserId();
                if (!currentUserId.equals(userId)) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN)
                            .body(Result.error(403, "不能为他人借书"));
                }
                borrowService.borrowBook(userId, bookId);
            }
            return ResponseEntity.ok(Result.success("借书成功", null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Result.error(400, e.getMessage()));
        }
    }

    @GetMapping("/admin/all")
    @RequireRole(1)
    @Operation(summary = "查询所有借阅记录", description = "管理员专用")
    public ResponseEntity<Result<List<Map<String, Object>>>> getAllBorrowRecords() {
        List<Map<String, Object>> list = borrowService.getAllBorrowRecords();
        return ResponseEntity.ok(Result.success(list));
    }

    @GetMapping("/borrowing")
    @Operation(summary = "查询用户当前借阅列表")
    public ResponseEntity<Result<List<Map<String, Object>>>> getUserBorrowingList(
            @RequestParam Long userId) {
        Long currentUserId = UserContext.getCurrentUserId();
        boolean isAdmin = UserContext.isAdmin();
        if (!isAdmin && !currentUserId.equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Result.error(403, "无权限查看他人借阅"));
        }
        List<Map<String, Object>> list = borrowService.getUserBorrowingList(userId);
        return ResponseEntity.ok(Result.success(list));
    }

    @GetMapping("/history")
    @Operation(summary = "查询用户借阅历史")
    public ResponseEntity<Result<List<Map<String, Object>>>> getUserHistory(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "20") int pageSize) {
        Long currentUserId = UserContext.getCurrentUserId();
        boolean isAdmin = UserContext.isAdmin();
        if (!isAdmin && !currentUserId.equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Result.error(403, "无权限查看他人借阅历史"));
        }
        List<Map<String, Object>> list = borrowService.getUserHistory(userId, pageNum, pageSize);
        return ResponseEntity.ok(Result.success(list));
    }

    @PostMapping("/return/{borrowId}")
    @Operation(summary = "归还图书")
    public ResponseEntity<Result<Map<String, Object>>> returnBook(@PathVariable Long borrowId) {
        try {
            BigDecimal fine = borrowService.returnBook(borrowId);
            Map<String, Object> data = new HashMap<>();
            data.put("fine", fine);
            data.put("message", fine.compareTo(BigDecimal.ZERO) > 0
                    ? "归还成功，逾期罚款 " + fine + " 元" : "归还成功");
            return ResponseEntity.ok(Result.success(data));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Result.error(400, e.getMessage()));
        }
    }

    @GetMapping("/statistics/category")
    @Operation(summary = "按分类统计借阅次数")
    public ResponseEntity<Result<List<Map<String, Object>>>> getBorrowCountByCategory() {
        List<Map<String, Object>> list = borrowService.getBorrowCountByCategory();
        return ResponseEntity.ok(Result.success(list));
    }

    @PostMapping("/renew/{borrowId}")
    @Operation(summary = "续借图书")
    public ResponseEntity<Result<String>> renewBook(@PathVariable Long borrowId) {
        try {
            borrowService.renewBook(borrowId);
            return ResponseEntity.ok(Result.success("续借成功，延长15天", null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Result.error(400, e.getMessage()));
        }
    }
}
