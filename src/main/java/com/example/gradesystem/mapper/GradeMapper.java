package com.example.gradesystem.mapper;

import com.example.gradesystem.dto.GradeVO;
import com.example.gradesystem.entity.Grade;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface GradeMapper {

    @Select("<script>" +
            "SELECT g.id id, g.score score, s.student_no studentNo, s.name name, s.class_name className, " +
            "c.course_name courseName, g.student_id studentId, g.course_id courseId " +
            "FROM grades g " +
            "JOIN students s ON s.id = g.student_id " +
            "JOIN courses  c ON c.id = g.course_id " +
            "<where>" +
            "<if test='className != null and className != \"\"'>AND s.class_name = #{className}</if>" +
            "<if test='courseName != null and courseName != \"\"'>AND c.course_name = #{courseName}</if>" +
            "</where> ORDER BY s.class_name, s.student_no</script>")
    List<GradeVO> findWithFilter(@Param("className") String className, @Param("courseName") String courseName);

    @Select("<script>" +
            "SELECT g.id id, g.score score, s.student_no studentNo, s.name name, s.class_name className, " +
            "c.course_name courseName, g.student_id studentId, g.course_id courseId " +
            "FROM grades g " +
            "JOIN students s ON s.id = g.student_id " +
            "JOIN courses  c ON c.id = g.course_id " +
            "WHERE g.student_id = #{studentId} ORDER BY c.course_no</script>")
    List<GradeVO> findByStudent(@Param("studentId") Long studentId);

    @Select("SELECT * FROM grades WHERE student_id=#{studentId} AND course_id=#{courseId}")
    Grade findByStudentAndCourse(@Param("studentId") Long studentId, @Param("courseId") Long courseId);

    @Select("SELECT * FROM grades WHERE id=#{id}")
    Grade findById(@Param("id") Long id);

    @Insert("INSERT INTO grades(student_id,course_id,score) VALUES(#{studentId},#{courseId},#{score})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Grade grade);

    @Update("UPDATE grades SET score=#{score} WHERE id=#{id}")
    int update(Grade grade);

    @Delete("DELETE FROM grades WHERE id=#{id}")
    int delete(@Param("id") Long id);

    @Delete("DELETE FROM grades WHERE student_id=#{studentId}")
    int deleteByStudent(@Param("studentId") Long studentId);

    @Delete("DELETE FROM grades WHERE course_id=#{courseId}")
    int deleteByCourse(@Param("courseId") Long courseId);
}
