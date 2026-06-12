package com.example.demo.annotation;

import java.lang.annotation.*;

/**
 * 权限注解
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequireRole {

    /**
     * 需要的角色：0-普通用户，1-管理员
     */
    int[] value() default {};

    /**
     * 是否必须登录
     */
    boolean login() default true;
}