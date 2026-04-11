package com.cyf.everhavenbe.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyf.everhavenbe.model.entity.Label;

import java.util.List;

/**
 * 标签服务接口
 */
public interface LabelService extends IService<Label> {
    
    /**
     * 获取房源的所有标签
     */
    List<Label> getByApartmentId(Long apartmentId);

    /**
     * 更新房源标签关联
     */
    void updateApartmentLabels(Long apartmentId, List<Long> labelIds);
}
