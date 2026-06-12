package com.example.demo.interceptor;

import com.example.demo.annotation.RequireRole;
import com.example.demo.context.UserContext;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtUtil jwtUtil;

    // 不需要拦截的路径（公开接口）
    private static final String[] PUBLIC_PATHS = {
            "/users/login",
            "/users/register"
    };

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 清空旧的上下文
        UserContext.clear();

        // 获取请求路径
        String path = request.getRequestURI();

        // 检查是否为公开路径（不需要登录）
        if (isPublicPath(path)) {
            return true;
        }

        // 非控制器方法直接放行
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        // 从 Authorization 头获取 JWT token
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return sendError(response, 401, "请先登录");
        }

        String token = authHeader.substring(7);
        Long userId;
        try {
            userId = jwtUtil.getUserIdFromToken(token);
        } catch (JwtException e) {
            return sendError(response, 401, "token无效或已过期，请重新登录");
        }

        // 查询用户
        User user = userMapper.selectById(userId);
        if (user == null) {
            return sendError(response, 401, "用户不存在");
        }

        // 检查用户状态
        if (user.getStatus() == 1) {
            return sendError(response, 403, "账号已被冻结");
        }

        // 存入 ThreadLocal
        UserContext.setCurrentUser(user);

        // 检查角色权限（如果有 @RequireRole 注解）
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            RequireRole requireRole = handlerMethod.getMethodAnnotation(RequireRole.class);

            if (requireRole != null) {
                int[] requiredRoles = requireRole.value();
                if (requiredRoles.length > 0) {
                    boolean hasRole = false;
                    for (int role : requiredRoles) {
                        if (user.getRole() == role) {
                            hasRole = true;
                            break;
                        }
                    }
                    if (!hasRole) {
                        return sendError(response, 403, "无权限访问，需要管理员权限");
                    }
                }
            }
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserContext.clear();
    }

    /**
     * 判断是否为公开路径
     */
    private boolean isPublicPath(String path) {
        for (String publicPath : PUBLIC_PATHS) {
            if (path.contains(publicPath)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 发送错误响应
     */
    private boolean sendError(HttpServletResponse response, int code, String message) throws Exception {
        response.setStatus(code);
        response.setContentType("application/json;charset=UTF-8");

        String json = String.format("{\"code\":%d,\"message\":\"%s\",\"data\":null}", code, message);

        PrintWriter writer = response.getWriter();
        writer.write(json);
        writer.flush();
        writer.close();
        return false;
    }
}