package com.example.gradesystem.service;

import com.example.gradesystem.common.BizException;
import com.example.gradesystem.entity.Course;
import com.example.gradesystem.mapper.CourseMapper;
import com.example.gradesystem.mapper.GradeMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private final CourseMapper courseMapper;
    private final GradeMapper gradeMapper;

    public CourseService(CourseMapper courseMapper, GradeMapper gradeMapper) {
        this.courseMapper = courseMapper;
        this.gradeMapper = gradeMapper;
    }

    public List<Course> list() {
        return courseMapper.findAll();
    }

    public Course add(Course c) {
        if (c.getCourseNo() == null || c.getCourseNo().isBlank()
                || c.getCourseName() == null || c.getCourseName().isBlank()) {
            throw new BizException("请填写课程编号与名称");
        }
        if (c.getCredit() == null) {
            c.setCredit(3.0);
        }
        if (courseMapper.findByNo(c.getCourseNo()) != null) {
            throw new BizException("课程编号已存在");
        }
        courseMapper.insert(c);
        return c;
    }

    public void delete(Long id) {
        // 先清理关联成绩，避免外键约束报错
        gradeMapper.deleteByCourse(id);
        courseMapper.delete(id);
    }
}
