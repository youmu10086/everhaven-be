package com.cyf.everhavenbe.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;
import java.util.Map;

public class JwtUtil {

    private static final String KEY = "EVERHAVEN";

    // Access Token 过期时间 1 小时
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1 * 60 * 60 * 1000;
    // Refresh Token 过期时间 7 天
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 7 * 24 * 60 * 60 * 1000;

    public static String genAccessToken(Map<String, Object> claims) {
        return JWT.create()
                .withClaim("claims", claims)
                .withExpiresAt(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRE_TIME))
                .sign(Algorithm.HMAC256(KEY));
    }

    public static String genRefreshToken(Map<String, Object> claims) {
        return JWT.create()
                .withClaim("claims", claims)
                .withExpiresAt(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRE_TIME))
                .sign(Algorithm.HMAC256(KEY));
    }

    // 保留旧方法以兼容（或者废弃），这里直接指向 genAccessToken
    public static String genToken(Map<String, Object> claims) {
        return genAccessToken(claims);
    }

    public static Map<String, Object> parseToken(String token) {
        return JWT.require(Algorithm.HMAC256(KEY))
                .build()
                .verify(token)
                .getClaim("claims")
                .asMap();
    }
}
