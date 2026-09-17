package com.example.gradesystem.entity;

import lombok.Data;

@Data
public class Student {
    private Long id;
    private String studentNo;
    private String name;
    private String gender;
    private String className;
}
