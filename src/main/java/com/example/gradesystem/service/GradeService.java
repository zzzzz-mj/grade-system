package com.example.gradesystem.service;

import com.example.gradesystem.common.BizException;
import com.example.gradesystem.dto.GradeUpsertRequest;
import com.example.gradesystem.dto.GradeVO;
import com.example.gradesystem.entity.Grade;
import com.example.gradesystem.mapper.GradeMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradeService {

    private final GradeMapper gradeMapper;

    public GradeService(GradeMapper gradeMapper) {
        this.gradeMapper = gradeMapper;
    }

    public List<GradeVO> list(String className, String courseName) {
        return gradeMapper.findWithFilter(className, courseName);
    }

    /** 录入 / 修改。id 为空为新增，依赖 UNIQUE(student_id, course_id) 防止重复录入。 */
    public Grade upsert(GradeUpsertRequest req) {
        if (req.getStudentId() == null || req.getCourseId() == null) {
            throw new BizException("请选择学生与课程");
        }
        if (req.getScore() == null || req.getScore() < 0 || req.getScore() > 100) {
            throw new BizException("成绩需在 0-100 之间");
        }
        if (req.getId() != null) {
            Grade exist = gradeMapper.findById(req.getId());
            if (exist == null) {
                throw new BizException("成绩记录不存在");
            }
            exist.setScore(req.getScore());
            gradeMapper.update(exist);
            return exist;
        }
        Grade dup = gradeMapper.findByStudentAndCourse(req.getStudentId(), req.getCourseId());
        if (dup != null) {
            throw new BizException("该生此课程成绩已存在，请修改而非重复录入");
        }
        Grade g = new Grade();
        g.setStudentId(req.getStudentId());
        g.setCourseId(req.getCourseId());
        g.setScore(req.getScore());
        gradeMapper.insert(g);
        return g;
    }

    public void delete(Long id) {
        gradeMapper.delete(id);
    }
}
