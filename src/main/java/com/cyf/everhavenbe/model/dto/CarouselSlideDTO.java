package com.cyf.everhavenbe.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CarouselSlideDTO {

    @NotBlank(message = "图片地址不能为空")
    private String image;

    private String imageDark;

    private String type = "hero";

    @NotBlank(message = "标题不能为空")
    private String title;

    private String subtitle;

    private String link;

    private String label;

    private String description;

    private Integer sortOrder = 0;

    private Boolean status = true;
}

