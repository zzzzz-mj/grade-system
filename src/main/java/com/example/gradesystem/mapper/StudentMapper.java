package com.example.gradesystem.mapper;

import com.example.gradesystem.entity.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentMapper {

    @Select("SELECT * FROM students ORDER BY student_no")
    List<Student> findAll();

    @Select("<script>" +
            "SELECT * FROM students " +
            "<where>" +
            "<if test='filter != null and filter != \"\"'>" +
            "AND (name LIKE CONCAT('%',#{filter},'%') OR student_no LIKE CONCAT('%',#{filter},'%') OR class_name LIKE CONCAT('%',#{filter},'%'))" +
            "</if>" +
            "</where> ORDER BY student_no</script>")
    List<Student> findByFilter(@Param("filter") String filter);

    @Select("SELECT * FROM students WHERE id = #{id}")
    Student findById(@Param("id") Long id);

    @Select("SELECT * FROM students WHERE student_no = #{no}")
    Student findByNo(@Param("no") String no);

    @Insert("INSERT INTO students(student_no,name,gender,class_name) VALUES(#{studentNo},#{name},#{gender},#{className})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Student student);

    @Update("UPDATE students SET name=#{name}, gender=#{gender}, class_name=#{className} WHERE id=#{id}")
    int update(Student student);

    @Delete("DELETE FROM students WHERE id=#{id}")
    int delete(@Param("id") Long id);
}
