package com.cyf.everhavenbe.mapper;

import com.cyf.everhavenbe.pojo.CarouselSlide;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CarouselSlideMapper {

    @Select("SELECT id, image, type, title, subtitle, sort_order AS sortOrder, status, " +
            "created_at AS createdAt, updated_at AS updatedAt " +
            "FROM carousel_slides ORDER BY sort_order ASC, id ASC")
    List<CarouselSlide> findAll();
}

