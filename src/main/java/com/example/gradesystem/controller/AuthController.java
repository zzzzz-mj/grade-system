package com.example.gradesystem.controller;

import com.example.gradesystem.common.Result;
import com.example.gradesystem.dto.LoginRequest;
import com.example.gradesystem.dto.UserVO;
import com.example.gradesystem.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public Result<UserVO> login(@RequestBody LoginRequest req) {
        return Result.ok(authService.login(req.getUsername(), req.getPassword(), req.getRole()));
    }
}
