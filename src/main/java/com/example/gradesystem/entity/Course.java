package com.example.gradesystem.entity;

import lombok.Data;

@Data
public class Course {
    private Long id;
    private String courseNo;
    private String courseName;
    private Double credit;
}
