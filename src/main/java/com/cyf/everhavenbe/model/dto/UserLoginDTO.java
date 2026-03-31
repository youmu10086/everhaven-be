package com.cyf.everhavenbe.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserLoginDTO {
    // 登录时可以是用户名或邮箱，去掉之前的正则校验，只保留非空校验
    @NotBlank(message = "用户名或邮箱不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;
}
