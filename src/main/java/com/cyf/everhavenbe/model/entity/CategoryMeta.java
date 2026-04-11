package com.cyf.everhavenbe.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "category_meta", uniqueConstraints = {@UniqueConstraint(columnNames={"category_group", "category_type"})})
public class CategoryMeta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_group", nullable = false, length = 50)
    private String categoryGroup;

    @Column(name = "category_type", nullable = false, length = 50)
    private String categoryType;

    @Column(length = 200)
    private String title;

    @Column(length = 500)
    private String description;

    @Column(length = 50)
    private String badge;

    @Column(length = 500)
    private String image;
}