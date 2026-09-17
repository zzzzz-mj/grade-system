package com.example.gradesystem.controller;

import com.example.gradesystem.common.Result;
import com.example.gradesystem.dto.RankRow;
import com.example.gradesystem.service.StatsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/stats")
public class StatsController {

    private final StatsService statsService;

    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    @GetMapping("/dashboard")
    public Result<Map<String, Object>> dashboard() {
        return Result.ok(statsService.dashboard());
    }

    @GetMapping("/course")
    public Result<List<Map<String, Object>>> courseStats() {
        return Result.ok(statsService.courseStats());
    }

    @GetMapping("/class")
    public Result<List<Map<String, Object>>> classStats() {
        return Result.ok(statsService.classStats());
    }

    @GetMapping("/rankings")
    public Result<List<RankRow>> rankings(@RequestParam(required = false) String className) {
        return Result.ok(statsService.rankings(className));
    }
}
