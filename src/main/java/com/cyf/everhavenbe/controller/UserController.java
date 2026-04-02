package com.cyf.everhavenbe.controller;

import com.cyf.everhavenbe.common.Result;
import com.cyf.everhavenbe.model.dto.UserLoginDTO;
import com.cyf.everhavenbe.model.dto.UserRegisterDTO;
import com.cyf.everhavenbe.model.dto.UserUpdateDTO;
import com.cyf.everhavenbe.model.entity.User;
import com.cyf.everhavenbe.model.vo.TokenVO;
import com.cyf.everhavenbe.service.UserService;
import com.cyf.everhavenbe.utils.RSAUtil;
import com.cyf.everhavenbe.utils.ThreadLocalUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
@Tag(name = "User Management", description = "用户管理相关操作")
@Slf4j
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/update")
    @Operation(summary = "更新用户信息", description = "更新当前登录用户的昵称、邮箱、头像等信息")
    public Result<User> update(@RequestBody @Validated UserUpdateDTO updateDTO) {
        Map<String, Object> claims = ThreadLocalUtil.get();
        Long id = ((Integer) claims.get("id")).longValue();
        
        userService.update(id, updateDTO.getNickname(), updateDTO.getEmail(), updateDTO.getUserPic());
        User updatedUser = userService.findById(id);
        return Result.success(updatedUser);
    }

    @GetMapping("/info")
    @Operation(summary = "获取当前用户信息")
    public Result<User> info() {
        Map<String, Object> claims = ThreadLocalUtil.get();
        Long id = ((Integer) claims.get("id")).longValue();
        User user = userService.findById(id);
        return Result.success(user);
    }

    @PostMapping("/register")
    @Operation(summary = "注册新用户", description = "使用提供的详细信息注册一个新账号")
    public Result<Void> register(@RequestBody @Validated UserRegisterDTO registerDTO) {
        try {
            // 解密密码
            String decryptedPassword = RSAUtil.decrypt(registerDTO.getPassword());
            
            // 查询用户名是否被占用
            User u = userService.findByUserName(registerDTO.getUsername());
            if (u != null) {
                return Result.error("用户名已被占用");
            }
            
            // 查询邮箱是否被占用
            u = userService.findByEmail(registerDTO.getEmail());
            if (u != null) {
                return Result.error("邮箱已被占用");
            }

            // 注册
            userService.register(registerDTO.getUsername(), decryptedPassword, registerDTO.getEmail());
            return Result.success();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("注册失败：" + e.getMessage());
        }
    }

    @PostMapping("/login")
    @Operation(summary = "用户登录", description = "用户身份验证，成功后返回访问令牌")
    public Result<TokenVO> login(@RequestBody @Validated UserLoginDTO loginDTO) {
        try {
            // 解密密码
            String decryptedPassword = RSAUtil.decrypt(loginDTO.getPassword());
            // 登录
            TokenVO tokenVO = userService.login(loginDTO.getUsername(), decryptedPassword);
            return Result.success(tokenVO);
        } catch (Exception e) {
           return Result.error(e.getMessage());
        }
    }

    @PostMapping("/refresh")
    @Operation(summary = "刷新令牌", description = "使用刷新令牌获取新的访问令牌")
    public Result<TokenVO> refreshToken(@RequestParam("refreshToken") String refreshToken) {
        try {
            TokenVO tokenVO = userService.refreshToken(refreshToken);
            return Result.success(tokenVO);
        } catch (Exception e) {
            return Result.error(401, "刷新令牌无效或已过期");
        }
    }
    
    @GetMapping("/public-key")
    @Operation(summary = "获取公钥", description = "获取用于前端密码加密的 RSA 公钥")
    public Result<String> getPublicKey() {
        return Result.success(RSAUtil.getPublicKey());
    }
}