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

    @org.apache.ibatis.annotations.Insert("INSERT INTO nav_categories (name) VALUES (#{name})")
    @org.apache.ibatis.annotations.Options(useGeneratedKeys = true, keyProperty = "id")
    void insertCategory(NavCategory category);

    @org.apache.ibatis.annotations.Update("UPDATE nav_categories SET name = #{name} WHERE id = #{id}")
    void updateCategory(NavCategory category);

    @org.apache.ibatis.annotations.Delete("DELETE FROM nav_categories WHERE id = #{id}")
    void deleteCategory(Long id);

    @org.apache.ibatis.annotations.Insert("INSERT INTO nav_items (category_id, title, description, link, image) VALUES (#{categoryId}, #{title}, #{description}, #{link}, #{image})")
    @org.apache.ibatis.annotations.Options(useGeneratedKeys = true, keyProperty = "id")
    void insertItem(NavItem item);

    @org.apache.ibatis.annotations.Update("UPDATE nav_items SET category_id = #{categoryId}, title = #{title}, description = #{description}, link = #{link}, image = #{image} WHERE id = #{id}")
    void updateItem(NavItem item);

    @org.apache.ibatis.annotations.Delete("DELETE FROM nav_items WHERE id = #{id}")
    void deleteItem(Long id);

    @org.apache.ibatis.annotations.Delete("DELETE FROM nav_items WHERE category_id = #{categoryId}")
    void deleteItemsByCategoryId(Long categoryId);
}

