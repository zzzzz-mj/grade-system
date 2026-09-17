package com.example.gradesystem.entity;

import lombok.Data;

@Data
public class Grade {
    private Long id;
    private Long studentId;
    private Long courseId;
    private Double score;
}
