package com.cyf.everhavenbe.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("apartment_reviews")
public class ApartmentReview {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    /** 房源ID */
    private Long apartmentId;
    
    /** 用户ID */
    private Long userId;
    
    /** 评分 (1-5) */
    private Integer score;
    
    /** 评价文字内容 */
    private String content;
    
    /** 评价图片列表 (JSON数组字符串) */
    private String images;
    
    /** 是否匿名 */
    private Boolean isAnonymous;
    
    /** 状态: 0-屏蔽, 1-正常 */
    private Integer status;
    
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}