package com.cyf.everhavenbe.mapper;

import com.cyf.everhavenbe.model.entity.NavCategory;
import com.cyf.everhavenbe.model.entity.NavItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NavMapper {

    @Select("SELECT id, name FROM nav_categories")
    List<NavCategory> findAllCategories();

    @Select("SELECT id, category_id AS categoryId, title, description, link, image FROM nav_items")
    List<NavItem> findAllItems();
}

