package com.cyf.everhavenbe.interceptors;

import com.cyf.everhavenbe.utils.JwtUtil;
import com.cyf.everhavenbe.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
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
            // http 响应码为 401
            response.setStatus(401);
            // 不放行
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 清理 ThreadLocal 中的数据，防止内存泄漏
        ThreadLocalUtil.remove();
    }
}
