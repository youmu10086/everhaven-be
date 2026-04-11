package com.cyf.everhavenbe.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyf.everhavenbe.model.entity.Label;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 标签Mapper接口
 */
@Mapper
public interface LabelMapper extends BaseMapper<Label> {
    
    /**
     * 根据房源ID查询关联的标签
     */
    @Select("SELECT l.* FROM labels l " +
            "JOIN apartment_labels al ON l.id = al.label_id " +
            "WHERE al.apartment_id = #{apartmentId}")
    List<Label> selectByApartmentId(Long apartmentId);
}
