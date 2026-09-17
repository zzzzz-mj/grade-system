package com.example.gradesystem.dto;

import lombok.Data;

/**
 * 登录返回的用户视图（不含密码）。
 */
@Data
public class UserVO {
    private Long id;
    private String username;
    private String role;
    private String name;
    private Long refId;

    public UserVO(Long id, String username, String role, String name, Long refId) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.name = name;
        this.refId = refId;
    }
}
