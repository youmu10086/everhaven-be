package com.cyf.everhavenbe.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyf.everhavenbe.mapper.LabelMapper;
import com.cyf.everhavenbe.model.entity.ApartmentLabel;
import com.cyf.everhavenbe.model.entity.Label;
import com.cyf.everhavenbe.service.LabelService;
import com.cyf.everhavenbe.mapper.ApartmentLabelMapper; // 注意：稍后会创建此类
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 标签服务实现类
 */
@Service
public class LabelServiceImpl extends ServiceImpl<LabelMapper, Label> implements LabelService {

    @Autowired
    private LabelMapper labelMapper;

    @Autowired
    private ApartmentLabelMapper apartmentLabelMapper;

    @Override
    public List<Label> getByApartmentId(Long apartmentId) {
        return labelMapper.selectByApartmentId(apartmentId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateApartmentLabels(Long apartmentId, List<Long> labelIds) {
        // 1. 删除旧关联
        apartmentLabelMapper.delete(new QueryWrapper<ApartmentLabel>().eq("apartment_id", apartmentId));
        
        // 2. 插入新关联
        if (labelIds != null && !labelIds.isEmpty()) {
            for (Long labelId : labelIds) {
                ApartmentLabel al = ApartmentLabel.builder()
                        .apartmentId(apartmentId)
                        .labelId(labelId)
                        .build();
                apartmentLabelMapper.insert(al);
            }
        }
    }
}
