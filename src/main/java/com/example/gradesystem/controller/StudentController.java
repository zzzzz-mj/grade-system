package com.example.gradesystem.controller;

import com.example.gradesystem.common.Result;
import com.example.gradesystem.dto.GradeVO;
import com.example.gradesystem.entity.Student;
import com.example.gradesystem.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public Result<List<Student>> list(@RequestParam(required = false) String filter) {
        return Result.ok(studentService.list(filter));
    }

    @PostMapping
    public Result<Student> add(@RequestBody Student student) {
        return Result.ok(studentService.add(student));
    }

    @PutMapping("/{id}")
    public Result<Student> update(@PathVariable Long id, @RequestBody Student student) {
        student.setId(id);
        return Result.ok(studentService.update(student));
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        studentService.delete(id);
        return Result.ok();
    }

    @GetMapping("/{id}/grades")
    public Result<List<GradeVO>> myGrades(@PathVariable Long id) {
        return Result.ok(studentService.myGrades(id));
    }

    @GetMapping("/{id}/stats")
    public Result<Map<String, Object>> myStats(@PathVariable Long id) {
        return Result.ok(studentService.myStats(id));
    }
}
