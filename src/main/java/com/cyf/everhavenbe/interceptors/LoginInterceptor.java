package com.cyf.everhavenbe.interceptors;

import com.cyf.everhavenbe.utils.JwtUtil;
import com.cyf.everhavenbe.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        // 令牌验证
        String token = request.getHeader("Authorization");
        // 如果是 Bearer token，截取掉前缀
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        try {
            Map<String, Object> claims = JwtUtil.parseToken(token);
            // 把业务数据存储到 ThreadLocal 中
            ThreadLocalUtil.set(claims);
            // 放行
            return true;
        } catch (Exception e) {
            String method = request.getMethod();
            String uri = request.getRequestURI();
            
            // 下列公共 GET 接口免登录访问
            if ("GET".equalsIgnoreCase(method)) {
                if (uri.startsWith("/apartments") || uri.startsWith("/home/") || uri.startsWith("/labels/") || uri.startsWith("/api/assistant") || uri.startsWith("/category_meta/")) {
                    return true;
                }
            }

            // http 响应码为 401
            response.setStatus(401);
            // 不放行
            return false;
        }
    }

    @Override
    public void afterCompletion(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler, @Nullable Exception ex) throws Exception {
        // 清理 ThreadLocal 中的数据，防止内存泄漏
        ThreadLocalUtil.remove();
    }
}
