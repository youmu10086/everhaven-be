package com.cyf.everhavenbe.mapper;

import com.cyf.everhavenbe.model.entity.CarouselSlide;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CarouselSlideMapper {

    @Select("SELECT id, image, image_dark AS imageDark, type, title, subtitle, `link`, `label`, description, " +
            "sort_order AS sortOrder, status, created_at AS createdAt, updated_at AS updatedAt " +
            "FROM carousel_slides ORDER BY sort_order ASC, id ASC")
    List<CarouselSlide> findAll();

    @Select("SELECT id, image, image_dark AS imageDark, type, title, subtitle, `link`, `label`, description, " +
            "sort_order AS sortOrder, status, created_at AS createdAt, updated_at AS updatedAt " +
            "FROM carousel_slides WHERE id = #{id}")
    CarouselSlide findById(Long id);

    @Insert("INSERT INTO carousel_slides(image, image_dark, type, title, subtitle, `link`, `label`, description, sort_order, status, created_at, updated_at) " +
            "VALUES(#{image}, #{imageDark}, #{type}, #{title}, #{subtitle}, #{link}, #{label}, #{description}, #{sortOrder}, #{status}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(CarouselSlide carouselSlide);

    @Update("UPDATE carousel_slides SET image=#{image}, image_dark=#{imageDark}, type=#{type}, title=#{title}, " +
            "subtitle=#{subtitle}, `link`=#{link}, `label`=#{label}, description=#{description}, " +
            "sort_order=#{sortOrder}, status=#{status}, updated_at=NOW() WHERE id=#{id}")
    void update(CarouselSlide carouselSlide);

    @Delete("DELETE FROM carousel_slides WHERE id=#{id}")
    void delete(Long id);
}
