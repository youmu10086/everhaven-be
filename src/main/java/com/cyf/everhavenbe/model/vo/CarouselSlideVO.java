package com.cyf.everhavenbe.vo;

import lombok.Data;

@Data
public class CarouselSlideVO {
    private Long id;
    private String image;
    private String imageDark;
    private String title;
    private String subtitle;
    private String link;
    private String label;
    private String description;
    private String type; // 'default' | 'cover' | 'scale-down' | 'hero'
}

