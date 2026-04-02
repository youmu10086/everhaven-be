package com.cyf.everhavenbe.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "用户更新数据传输对象")
public class UserUpdateDTO {

    @Size(min = 2, max = 20, message = "昵称长度必须在2-20之间")
    @Schema(description = "昵称")
    private String nickname;

    @Email(message = "邮箱格式不正确")
    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "头像地址")
    private String userPic;
}
