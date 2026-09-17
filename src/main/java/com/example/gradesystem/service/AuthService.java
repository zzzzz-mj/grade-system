package com.example.gradesystem.service;

import com.example.gradesystem.common.BizException;
import com.example.gradesystem.dto.UserVO;
import com.example.gradesystem.entity.User;
import com.example.gradesystem.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserMapper userMapper;

    public AuthService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public UserVO login(String username, String password, String role) {
        if (username == null || password == null || role == null) {
            throw new BizException("参数不完整");
        }
        User user = userMapper.findByUsernameAndRole(username, role);
        if (user == null) {
            throw new BizException("账号不存在");
        }
        if (!user.getPassword().equals(password)) {
            throw new BizException("密码错误");
        }
        return new UserVO(user.getId(), user.getUsername(), user.getRole(), user.getName(), user.getRefId());
    }
}
