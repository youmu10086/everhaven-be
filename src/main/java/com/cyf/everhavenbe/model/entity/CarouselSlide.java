package com.cyf.everhavenbe.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "carousel_slides")
@Data  // ← 自动生成 getter / setter / toString / equals / hashCode
public class CarouselSlide {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image", nullable = false, length = 500)
    private String image;

    @Column(name = "image_dark", length = 500)
    private String imageDark;

    @Column(name = "type", length = 50)
    private String type = "hero";

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Column(name = "subtitle", length = 500)
    private String subtitle;

    @Column(name = "link", length = 500)
    private String link;

    @Column(name = "label", length = 100)
    private String label;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "sort_order")
    private Integer sortOrder = 0;

    @Column(name = "status")
    private Boolean status = true;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}