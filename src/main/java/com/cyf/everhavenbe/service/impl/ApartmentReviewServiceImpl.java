package com.cyf.everhavenbe.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyf.everhavenbe.mapper.ApartmentMapper;
import com.cyf.everhavenbe.mapper.ApartmentReviewMapper;
import com.cyf.everhavenbe.model.entity.Apartment;
import com.cyf.everhavenbe.model.entity.ApartmentReview;
import com.cyf.everhavenbe.service.ApartmentReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApartmentReviewServiceImpl extends ServiceImpl<ApartmentReviewMapper, ApartmentReview> implements ApartmentReviewService {

    private final ApartmentMapper apartmentMapper;

    @Override
    @Transactional
    public void submitReview(ApartmentReview review) {
        // 1. 保存评价明细
        this.save(review);

        // 2. 重新计算并更新房源冗余评分 (简单实现：同步更新，后续可优化为异步)
        Long apartmentId = review.getApartmentId();
        
        // 查询该房源所有正常状态的评分
        List<ApartmentReview> reviews = this.list(new LambdaQueryWrapper<ApartmentReview>()
                .eq(ApartmentReview::getApartmentId, apartmentId)
                .eq(ApartmentReview::getStatus, 1));

        if (!reviews.isEmpty()) {
            double averageScore = reviews.stream()
                    .mapToInt(ApartmentReview::getScore)
                    .average()
                    .orElse(0.0);
            
            // 保留一位小数
            averageScore = Math.round(averageScore * 10.0) / 10.0;
            
            Apartment apartment = apartmentMapper.selectById(apartmentId);
            if (apartment != null) {
                apartment.setRating(averageScore);
                apartment.setReviewCount(reviews.size());
                apartmentMapper.updateById(apartment);
            }
        }
    }
}