package com.example.gradesystem.dto;

import lombok.Data;

/**
 * 班级排名行（含班级内名次）。
 */
@Data
public class RankRow {
    private Long id;
    private String studentNo;
    private String name;
    private String className;
    private Double avgScore;
    private Double totalScore;
    private int rank;
}
