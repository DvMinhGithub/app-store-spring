package com.mdv.appstore.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.*;

import com.mdv.appstore.dto.request.UserLoginRequest;
import com.mdv.appstore.dto.request.UserRegisterRequest;
import com.mdv.appstore.dto.response.ApiResponse;
import com.mdv.appstore.dto.response.LoginResponse;
import com.mdv.appstore.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${app.api.base-url}/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody UserLoginRequest user) {
        return ApiResponse.success(authService.login(user), "Login successful");
    }

    @PostMapping("/register")
    public ApiResponse<Void> register(@RequestBody UserRegisterRequest user) {
        authService.register(user);
        return ApiResponse.success("Registration successful");
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody(required = false) Map<String, String> body) {
        String token = authHeader.substring(7);
        authService.logout(token, body);
        return ApiResponse.success("Logout successful");
    }
}
