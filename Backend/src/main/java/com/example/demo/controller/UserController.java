package com.example.demo.controller;

import com.example.demo.common.Result;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户控制器
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public ResponseEntity<Result<Map<String, Object>>> login(@RequestBody User loginUser) {
        User user = userService.login(loginUser.getUsername(), loginUser.getPassword());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Result.error(401, "用户名或密码错误"));
        }

        Map<String, Object> data = new HashMap<>();
        data.put("id", user.getId());
        data.put("username", user.getUsername());
        data.put("realName", user.getRealName());
        data.put("role", user.getRole());
        data.put("maxBorrowCount", user.getMaxBorrowCount());
        data.put("token", jwtUtil.generateToken(user.getId(), user.getUsername(), user.getRole()));

        return ResponseEntity.ok(Result.success("登录成功", data));
    }

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public ResponseEntity<Result<String>> register(@RequestBody User user) {
        try {
            boolean success = userService.register(user);
            if (success) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(Result.success("注册成功", null));
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Result.error("注册失败"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Result.error(409, e.getMessage()));
        }
    }

    /**
     * 根据ID查询用户
     */
    @GetMapping("/{id}")
    public ResponseEntity<Result<User>> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Result.error(404, "用户不存在"));
        }
        return ResponseEntity.ok(Result.success(user));
    }

    /**
     * 查询所有用户
     */
    @GetMapping
    public ResponseEntity<Result<List<User>>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(Result.success(users));
    }

    /**
     * 更新用户信息
     */
    @PutMapping("/{id}")
    public ResponseEntity<Result<String>> updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        boolean success = userService.updateUser(user);
        if (success) {
            return ResponseEntity.ok(Result.success("更新成功", null));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Result.error("更新失败"));
    }

    /**
     * 删除用户
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Result<String>> deleteUser(@PathVariable Long id) {
        boolean success = userService.deleteUser(id);
        if (success) {
            return ResponseEntity.ok(Result.success("删除成功", null));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Result.error("删除失败"));
    }

    /**
     * 拉黑用户
     */
    @PutMapping("/{id}/blacklist")
    public ResponseEntity<Result<String>> addToBlacklist(@PathVariable Long id) {
        boolean success = userService.addToBlacklist(id);
        if (success) {
            return ResponseEntity.ok(Result.success("已加入黑名单", null));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Result.error("操作失败"));
    }

    /**
     * 移出黑名单
     */
    @DeleteMapping("/{id}/blacklist")
    public ResponseEntity<Result<String>> removeFromBlacklist(@PathVariable Long id) {
        boolean success = userService.removeFromBlacklist(id);
        if (success) {
            return ResponseEntity.ok(Result.success("已移出黑名单", null));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Result.error("操作失败"));
    }
}