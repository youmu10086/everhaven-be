package com.cyf.everhavenbe.service;

import com.cyf.everhavenbe.model.entity.User;
import com.cyf.everhavenbe.model.vo.TokenVO;

public interface UserService {
    // 根据用户名查询用户
    User findByUserName(String username);

    // 根据邮箱查询用户
    User findByEmail(String email);

    void register(String username, String password, String email);

    // 修改为返回 TokenVO
    TokenVO login(String identifier, String password);

    // 刷新 Token
    TokenVO refreshToken(String refreshToken);
}
