package com.example.gradesystem.mapper;

import com.example.gradesystem.dto.RankRow;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface StatsMapper {

    @Select("SELECT COUNT(*) FROM students")
    int countStudents();

    @Select("SELECT COUNT(*) FROM courses")
    int countCourses();

    @Select("SELECT COUNT(*) FROM grades")
    int countGrades();

    @Select("SELECT AVG(score) FROM grades")
    Double avgScore();

    @Select("SELECT COUNT(*) FROM grades WHERE score >= 60")
    int countPass();

    @Select("SELECT s.name name, AVG(g.score) avgScore " +
            "FROM grades g JOIN students s ON s.id=g.student_id " +
            "GROUP BY s.id ORDER BY avgScore DESC LIMIT 1")
    Map<String, Object> topStudent();

    @Select("<script>" +
            "SELECT c.course_name courseName, COUNT(*) cnt, AVG(g.score) avg, MAX(g.score) mx, MIN(g.score) mn, " +
            "SUM(CASE WHEN g.score >= 60 THEN 1 ELSE 0 END) * 100.0 / COUNT(*) pass " +
            "FROM grades g JOIN courses c ON c.id = g.course_id " +
            "GROUP BY c.id ORDER BY avg DESC</script>")
    List<Map<String, Object>> courseStats();

    @Select("SELECT s.class_name className, AVG(g.score) avg, COUNT(*) cnt " +
            "FROM grades g JOIN students s ON s.id = g.student_id " +
            "GROUP BY s.class_name ORDER BY avg DESC")
    List<Map<String, Object>> classStats();

    @Select("<script>" +
            "SELECT s.id id, s.student_no studentNo, s.name name, s.class_name className, " +
            "AVG(g.score) avgScore, SUM(g.score) totalScore " +
            "FROM students s JOIN grades g ON g.student_id = s.id " +
            "<where><if test='className != null and className != \"\"'>AND s.class_name = #{className}</if></where> " +
            "GROUP BY s.id ORDER BY avgScore DESC</script>")
    List<RankRow> rankings(@Param("className") String className);

    @Select("SELECT DISTINCT class_name FROM students ORDER BY class_name")
    List<String> classes();
}
