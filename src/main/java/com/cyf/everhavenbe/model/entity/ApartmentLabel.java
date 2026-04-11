package com.cyf.everhavenbe.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Builder;
import lombok.Data;
import java.io.Serializable;

/**
 * 房源标签管理实体
 */
@Data
@TableName("apartment_labels")
@Builder
public class ApartmentLabel implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 房源ID
     */
    private Long apartmentId;

    /**
     * 标签ID
     */
    private Long labelId;
}
