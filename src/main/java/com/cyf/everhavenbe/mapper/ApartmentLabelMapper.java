package com.cyf.everhavenbe.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyf.everhavenbe.model.entity.ApartmentLabel;
import org.apache.ibatis.annotations.Mapper;

/**
 * 房源标签管理Mapper
 */
@Mapper
public interface ApartmentLabelMapper extends BaseMapper<ApartmentLabel> {
}
