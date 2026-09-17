package com.example.gradesystem.mapper;

import com.example.gradesystem.entity.Course;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CourseMapper {

    @Select("SELECT * FROM courses ORDER BY course_no")
    List<Course> findAll();

    @Select("SELECT * FROM courses WHERE id = #{id}")
    Course findById(@Param("id") Long id);

    @Select("SELECT * FROM courses WHERE course_no = #{no}")
    Course findByNo(@Param("no") String no);

    @Insert("INSERT INTO courses(course_no,course_name,credit) VALUES(#{courseNo},#{courseName},#{credit})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Course course);

    @Delete("DELETE FROM courses WHERE id=#{id}")
    int delete(@Param("id") Long id);
}
