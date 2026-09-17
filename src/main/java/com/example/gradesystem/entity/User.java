package com.example.gradesystem.entity;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String username;
    private String password;
    private String role;       // teacher | student
    private String name;
    private Long refId;         // 关联 students.id（学生账号）
}
