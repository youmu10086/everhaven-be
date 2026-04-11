package com.cyf.everhavenbe.mapper;

import com.cyf.everhavenbe.model.entity.Activity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ActivityMapper {

    @Insert("INSERT INTO activities (title, description, image, tag, type, valid_until, status) " +
            "VALUES (#{title}, #{description}, #{image}, #{tag}, #{type}, #{validUntil}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Activity activity);

    @Update("UPDATE activities SET title=#{title}, description=#{description}, image=#{image}, tag=#{tag}, " +
            "type=#{type}, valid_until=#{validUntil}, status=#{status} WHERE id=#{id}")
    void update(Activity activity);

    @Delete("DELETE FROM activities WHERE id=#{id}")
    void delete(Long id);

    @Select("SELECT * FROM activities WHERE id=#{id}")
    Activity findById(Long id);

    @Select("SELECT * FROM activities")
    List<Activity> findAll();
}