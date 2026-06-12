package com.example.demo.context;

import com.example.demo.entity.User;

/**
 * 用户上下文（ThreadLocal）
 */
public class UserContext {

    private static final ThreadLocal<User> CURRENT_USER = new ThreadLocal<>();

    public static void setCurrentUser(User user) {
        CURRENT_USER.set(user);
    }

    public static User getCurrentUser() {
        return CURRENT_USER.get();
    }

    public static Long getCurrentUserId() {
        User user = getCurrentUser();
        return user != null ? user.getId() : null;
    }

    public static boolean isAdmin() {
        User user = getCurrentUser();
        return user != null && user.getRole() != null && user.getRole() == 1;
    }

    public static void clear() {
        CURRENT_USER.remove();
    }
}