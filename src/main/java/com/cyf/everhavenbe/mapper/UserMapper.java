package com.cyf.everhavenbe.mapper;

import com.cyf.everhavenbe.model.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    // 根据用户名查询用户
    @Select("SELECT id, username, password, nickname, email, user_pic, create_time, update_time " +
            "FROM users WHERE username = #{username}")
    User findByUserName(@Param("username") String username);

    // 根据邮箱查询用户
    @Select("SELECT id, username, password, nickname, email, user_pic, create_time, update_time " +
            "FROM users WHERE email = #{email}")
    User findByEmail(@Param("email") String email);

    // 根据用户名或邮箱查询用户
    @Select("SELECT id, username, password, nickname, email, user_pic, create_time, update_time " +
            "FROM users WHERE username = #{identifier} OR email = #{identifier}")
    User findByUserNameOrEmail(@Param("identifier") String identifier);

    // 根据ID查询用户
    @Select("SELECT id, username, password, nickname, email, user_pic, create_time, update_time, role " +
            "FROM users WHERE id = #{id}")
    User findById(@Param("id") Long id);

    // 新增用户
    @Insert("INSERT INTO users(username, password, email, create_time, update_time) " +
            "VALUES(#{username}, #{password}, #{email}, NOW(), NOW())")
    void add(@Param("username") String username, @Param("password") String password, @Param("email") String email);

    // 更新用户信息 (MyBatis 动态SQL在这里较长，为了工具准确性使用简单格式，实际生产建议使用XML或SQLProvider)
    @org.apache.ibatis.annotations.Update("UPDATE users SET nickname = #{nickname}, email = #{email}, user_pic = #{userPic}, update_time = NOW() WHERE id = #{id}")
    void update(User user);
}
