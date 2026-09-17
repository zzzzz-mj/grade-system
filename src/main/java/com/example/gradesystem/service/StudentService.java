package com.example.gradesystem.service;

import com.example.gradesystem.common.BizException;
import com.example.gradesystem.dto.GradeVO;
import com.example.gradesystem.dto.RankRow;
import com.example.gradesystem.entity.Student;
import com.example.gradesystem.entity.User;
import com.example.gradesystem.mapper.GradeMapper;
import com.example.gradesystem.mapper.StudentMapper;
import com.example.gradesystem.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class StudentService {

    private final StudentMapper studentMapper;
    private final UserMapper userMapper;
    private final GradeMapper gradeMapper;
    private final StatsService statsService;

    public StudentService(StudentMapper studentMapper, UserMapper userMapper,
                          GradeMapper gradeMapper, StatsService statsService) {
        this.studentMapper = studentMapper;
        this.userMapper = userMapper;
        this.gradeMapper = gradeMapper;
        this.statsService = statsService;
    }

    public List<Student> list(String filter) {
        return studentMapper.findByFilter(filter);
    }

    public Student add(Student s) {
        if (isBlank(s.getStudentNo()) || isBlank(s.getName()) || isBlank(s.getGender()) || isBlank(s.getClassName())) {
            throw new BizException("请填写完整信息");
        }
        if (studentMapper.findByNo(s.getStudentNo()) != null) {
            throw new BizException("学号已存在，请勿重复添加");
        }
        studentMapper.insert(s);
        // 同步生成学生登录账号（学号 + 默认密码）
        User u = new User();
        u.setUsername(s.getStudentNo());
        u.setPassword("123456");
        u.setRole("student");
        u.setName(s.getName());
        u.setRefId(s.getId());
        userMapper.insert(u);
        return s;
    }

    public Student update(Student s) {
        studentMapper.update(s);
        return s;
    }

    public void delete(Long id) {
        gradeMapper.deleteByStudent(id);
        userMapper.deleteByRef(id);
        studentMapper.delete(id);
    }

    public List<GradeVO> myGrades(Long studentId) {
        return gradeMapper.findByStudent(studentId);
    }

    /** 学生个人统计：平均分、已修门数、班级内名次 */
    public Map<String, Object> myStats(Long studentId) {
        List<GradeVO> grades = gradeMapper.findByStudent(studentId);
        double avg = grades.stream().mapToDouble(GradeVO::getScore).average().orElse(0);
        int count = grades.size();
        Student me = studentMapper.findById(studentId);
        String className = me != null ? me.getClassName() : "";
        List<RankRow> classRanks = statsService.rankings(className);
        int rank = classRanks.stream()
                .filter(r -> r.getId() != null && r.getId().equals(studentId))
                .mapToInt(RankRow::getRank)
                .findFirst().orElse(0);

        Map<String, Object> res = new LinkedHashMap<>();
        res.put("avg", String.format("%.1f", avg));
        res.put("count", count);
        res.put("rank", rank == 0 ? "—" : rank);
        res.put("class", className);
        return res;
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
