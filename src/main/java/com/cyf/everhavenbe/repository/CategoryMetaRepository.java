package com.cyf.everhavenbe.repository;

import com.cyf.everhavenbe.model.entity.CategoryMeta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryMetaRepository extends JpaRepository<CategoryMeta, Long> {
    CategoryMeta findByCategoryGroupAndCategoryType(String categoryGroup, String categoryType);
}