package com.example.gradesystem.dto;

import lombok.Data;

/**
 * 成绩录入/修改请求。id 为空表示新增，非空表示修改。
 */
@Data
public class GradeUpsertRequest {
    private Long id;
    private Long studentId;
    private Long courseId;
    private Double score;
}
