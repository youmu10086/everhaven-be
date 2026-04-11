package com.cyf.everhavenbe.model.vo;

import com.cyf.everhavenbe.model.entity.Apartment;
import com.cyf.everhavenbe.model.entity.Label;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;

/**
 * 房源显示模型，包含标签列表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "房源展示对象")
public class ApartmentVO extends Apartment {
    
    @Schema(description = "标签列表")
    private List<Label> labels;

    /**
     * 将 Entity 转换为 VO
     */
    public static ApartmentVO fromEntity(Apartment apartment, List<Label> labels) {
        ApartmentVO vo = new ApartmentVO();
        // 属性拷贝（可根据需要使用 BeanUtils）
        vo.setId(apartment.getId());
        vo.setTitle(apartment.getTitle());
        vo.setDescription(apartment.getDescription());
        vo.setLocation(apartment.getLocation());
        vo.setPrice(apartment.getPrice());
        vo.setPriceUnit(apartment.getPriceUnit());
        vo.setImage(apartment.getImage());
        vo.setTag(apartment.getTag());
        vo.setCategoryGroup(apartment.getCategoryGroup());
        vo.setCategoryType(apartment.getCategoryType());
        vo.setStatus(apartment.getStatus());
        vo.setRating(apartment.getRating());
        vo.setReviewCount(apartment.getReviewCount());
        vo.setCreateTime(apartment.getCreateTime());
        vo.setUpdateTime(apartment.getUpdateTime());
        vo.setLabels(labels);
        return vo;
    }
}
