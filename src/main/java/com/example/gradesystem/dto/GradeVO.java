package com.example.gradesystem.dto;

import lombok.Data;

/**
 * 成绩列表视图（联表查询后的展示对象）。
 */
@Data
public class GradeVO {
    private Long id;
    private Double score;
    private String studentNo;
    private String name;
    private String className;
    private String courseName;
    private Long studentId;
    private Long courseId;
}
