package com.cyf.everhavenbe.vo;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserVO {
    private Long id;
    private String username;
    private String nickname;
    private String email;
    private String userPic;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

