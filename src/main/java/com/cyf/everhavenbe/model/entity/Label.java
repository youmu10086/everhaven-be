package com.cyf.everhavenbe.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Builder;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 标签实体
 * @author EverHaven Fullstack Agent
 */
@Data
@TableName("labels")
@Builder
public class Label implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 标签名称
     */
    private String name;

    /**
     * 标签类型: default, facility, service
     */
    private String type;

    /**
     * 标签颜色 (16进制)
     */
    private String color;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}
