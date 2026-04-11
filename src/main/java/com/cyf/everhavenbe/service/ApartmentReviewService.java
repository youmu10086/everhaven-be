package com.cyf.everhavenbe.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyf.everhavenbe.model.entity.ApartmentReview;

public interface ApartmentReviewService extends IService<ApartmentReview> {
    /**
     * 提交评价并更新房源冗余评分
     */
    void submitReview(ApartmentReview review);
}