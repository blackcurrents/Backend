package com.example.demo.config;

import com.example.demo.interceptor.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/**")  // 拦截所有请求
                .excludePathPatterns(
                        "/users/login",
                        "/users/register",
                        "/swagger-ui/**",      // Swagger UI 资源
                        "/swagger-ui.html",    // Swagger UI 入口
                        "/v3/api-docs/**",     // OpenAPI 文档
                        "/doc.html",            // 如使用 Knife4j
                        "/alipay/notify"        // 支付宝异步回调，不走JWT
                );  // 不排除任何路径，由拦截器内部判断
    }
}