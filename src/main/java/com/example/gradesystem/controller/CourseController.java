package com.example.gradesystem.controller;

import com.example.gradesystem.common.Result;
import com.example.gradesystem.entity.Course;
import com.example.gradesystem.service.CourseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public Result<List<Course>> list() {
        return Result.ok(courseService.list());
    }

    @PostMapping
    public Result<Course> add(@RequestBody Course course) {
        return Result.ok(courseService.add(course));
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        courseService.delete(id);
        return Result.ok();
    }
}
