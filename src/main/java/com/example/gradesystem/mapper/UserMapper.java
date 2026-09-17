package com.example.gradesystem.mapper;

import com.example.gradesystem.entity.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM users WHERE username=#{username} AND role=#{role}")
    User findByUsernameAndRole(@Param("username") String username, @Param("role") String role);

    @Select("SELECT * FROM users WHERE username=#{username}")
    User findByUsername(@Param("username") String username);

    @Insert("INSERT INTO users(username,password,role,name,ref_id) VALUES(#{username},#{password},#{role},#{name},#{refId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);

    @Delete("DELETE FROM users WHERE ref_id=#{refId} AND role='student'")
    int deleteByRef(@Param("refId") Long refId);
}
