package com.example.gradesystem.service;

import com.example.gradesystem.dto.RankRow;
import com.example.gradesystem.mapper.StatsMapper;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatsService {

    private final StatsMapper statsMapper;

    public StatsService(StatsMapper statsMapper) {
        this.statsMapper = statsMapper;
    }

    public Map<String, Object> dashboard() {
        int totalStu = statsMapper.countStudents();
        int totalCo = statsMapper.countCourses();
        int totalG = statsMapper.countGrades();
        Double avg = statsMapper.avgScore();
        int pass = statsMapper.countPass();
        Map<String, Object> top = statsMapper.topStudent();

        Map<String, Object> res = new LinkedHashMap<>();
        res.put("totalStu", totalStu);
        res.put("totalCo", totalCo);
        res.put("totalG", totalG);
        res.put("avg", avg == null ? "0.0" : String.format("%.1f", avg));
        res.put("passRate", totalG == 0 ? "0.0" : String.format("%.1f", pass * 100.0 / totalG));
        res.put("top", top == null ? "—" : top.get("name") + " (" + String.format("%.1f", ((Number) top.get("avgScore")).doubleValue()) + ")");
        return res;
    }

    public List<Map<String, Object>> courseStats() {
        return statsMapper.courseStats();
    }

    public List<Map<String, Object>> classStats() {
        return statsMapper.classStats();
    }

    /** 班级排名：在 SQL 按平均分全局排序的基础上，计算每个班级内的名次。 */
    public List<RankRow> rankings(String className) {
        List<RankRow> rows = statsMapper.rankings(className);
        Map<String, Integer> counter = new LinkedHashMap<>();
        for (RankRow r : rows) {
            String c = r.getClassName();
            int idx = counter.getOrDefault(c, 0) + 1;
            r.setRank(idx);
            counter.put(c, idx);
        }
        return rows;
    }

    public List<String> classes() {
        return statsMapper.classes();
    }
}
