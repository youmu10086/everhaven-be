package com.cyf.everhavenbe.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("apartments")
public class Apartment {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String title;
    private String description;
    private String location;
    private BigDecimal price;
    private String priceUnit;
    private String image;
    private String tag;
    private String categoryGroup;
    private String categoryType;
    private Integer status;
    private Double rating;
    private Integer reviewCount;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
