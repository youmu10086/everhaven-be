package com.cyf.everhavenbe.config;

import com.cyf.everhavenbe.interceptors.LoginInterceptor;
import com.cyf.everhavenbe.interceptors.LoggingInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Autowired
    private LoggingInterceptor loggingInterceptor;

    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        // 注册请求日志拦截器 (放在最前面)
        registry.addInterceptor(loggingInterceptor)
                .addPathPatterns("/**");

        // 注册权限拦截器
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/user/login", "/user/register", "/user/public-key", "/user/refresh", "/media/**", "/uploads/**", "/", "/index.html", "/favicon.ico", "/error", "/activities", "/activities/**");
    }
}
