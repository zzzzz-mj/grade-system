package com.example.gradesystem.controller;

import com.example.gradesystem.common.Result;
import com.example.gradesystem.dto.GradeUpsertRequest;
import com.example.gradesystem.dto.GradeVO;
import com.example.gradesystem.entity.Grade;
import com.example.gradesystem.service.GradeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grades")
public class GradeController {

    private final GradeService gradeService;

    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @GetMapping
    public Result<List<GradeVO>> list(@RequestParam(required = false) String className,
                                      @RequestParam(required = false) String courseName) {
        return Result.ok(gradeService.list(className, courseName));
    }

    @PostMapping
    public Result<Grade> add(@RequestBody GradeUpsertRequest req) {
        return Result.ok(gradeService.upsert(req));
    }

    @PutMapping("/{id}")
    public Result<Grade> update(@PathVariable Long id, @RequestBody GradeUpsertRequest req) {
        req.setId(id);
        return Result.ok(gradeService.upsert(req));
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        gradeService.delete(id);
        return Result.ok();
    }
}
