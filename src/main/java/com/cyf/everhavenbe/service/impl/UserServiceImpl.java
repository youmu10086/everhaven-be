package com.cyf.everhavenbe.service.impl;

import com.cyf.everhavenbe.mapper.UserMapper;
import com.cyf.everhavenbe.model.entity.User;
import com.cyf.everhavenbe.model.vo.TokenVO;
import com.cyf.everhavenbe.service.UserService;
import com.cyf.everhavenbe.utils.JwtUtil;
import com.cyf.everhavenbe.utils.MD5Util;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User findByUserName(String username) {
        User u = userMapper.findByUserName(username);
        return u;
    }

    @Override
    public User findByEmail(String email) {
        return userMapper.findByEmail(email);
    }

    @Override
    public void register(String username, String password, String email) {
        String md5String = MD5Util.getMD5String(password);
        userMapper.add(username, md5String, email);
    }

    @Override
    public TokenVO login(String identifier, String password) {
        // 1. 查询用户
        User user = userMapper.findByUserNameOrEmail(identifier);
        if (user == null) {
            throw new RuntimeException("用户名或邮箱不存在");
        }

        // 2. 校验密码
        String md5Password = MD5Util.getMD5String(password);
        if (!md5Password.equals(user.getPassword())) {
            throw new RuntimeException("密码错误");
        }

        // 3. 生成 token
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("username", user.getUsername());
        
        String accessToken = JwtUtil.genAccessToken(claims);
        String refreshToken = JwtUtil.genRefreshToken(claims);
        return new TokenVO(accessToken, refreshToken);
    }

    @Override
    public TokenVO refreshToken(String refreshToken) {
        try {
            // 验证并解析 refreshToken
            Map<String, Object> claims = JwtUtil.parseToken(refreshToken);
            
            // 重新生成 Token (这里可以选择只生成 accessToken，也可以都重新生成，这里都重新生成)
            // 注意：claims解析出来可能带有一些JWT内部字段，需要重新组装只包含业务字段的claims
            Map<String, Object> newClaims = new HashMap<>();
            newClaims.put("id", claims.get("id"));
            newClaims.put("username", claims.get("username"));

            String newAccessToken = JwtUtil.genAccessToken(newClaims);
            // 这里也可以选择不刷新 refreshToken，延长 refreshToken 有效期通常需要持久化支持或者新的 refreshToken
            String newRefreshToken = JwtUtil.genRefreshToken(newClaims); 
            
            return new TokenVO(newAccessToken, newRefreshToken);
        } catch (Exception e) {
            throw new RuntimeException("无效的刷新令牌");
        }
    }
}
