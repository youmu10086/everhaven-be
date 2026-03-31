package com.cyf.everhavenbe.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "nav_items")
public class NavItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(length = 500)
    private String description;

    @Column(length = 500)
    private String link;

    @Column(length = 500)
    private String image;
}

