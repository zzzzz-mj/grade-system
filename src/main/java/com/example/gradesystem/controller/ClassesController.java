package com.example.gradesystem.controller;

import com.example.gradesystem.common.Result;
import com.example.gradesystem.service.StatsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/classes")
public class ClassesController {

    private final StatsService statsService;

    public ClassesController(StatsService statsService) {
        this.statsService = statsService;
    }

    @GetMapping
    public Result<List<String>> classes() {
        return Result.ok(statsService.classes());
    }
}
