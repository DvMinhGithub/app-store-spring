package com.mdv.mybatis.controller;

import com.mdv.mybatis.model.dto.UserDTO;
import com.mdv.mybatis.model.request.LoginRequest;
import com.mdv.mybatis.model.request.UserRequest;
import com.mdv.mybatis.model.response.ApiResponse;
import com.mdv.mybatis.servicer.UserService;
import jakarta.annotation.Nullable;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ApiResponse<Object> createUser(@RequestBody UserRequest user) {
        userService.createUser(user);
        return ApiResponse.builder()
                .code(HttpStatus.CREATED.value())
                .message("User created successfully")
                .build();
    }

    @PostMapping("/login")
    public ApiResponse<UserDTO> login(@RequestBody LoginRequest user) {
        return ApiResponse.<UserDTO>builder()
                .code(HttpStatus.OK.value())
                .message("User logged in successfully")
                .data(userService.login(user))
                .build();
    }

    @GetMapping
    public ApiResponse<List<UserDTO>> findAll() {
        return ApiResponse.<List<UserDTO>>builder()
                .code(HttpStatus.OK.value())
                .message("User fetched successfully")
                .data(userService.findAll())
                .build();
    }

    @GetMapping({"/search"})
    public ApiResponse<UserDTO> findByEmailOrPhone(
            @RequestParam("email") @Nullable String email,
            @RequestParam("phone") @Nullable String phone) {
        return ApiResponse.<UserDTO>builder()
                .code(HttpStatus.OK.value())
                .message("User fetched successfully")
                .data(userService.findByEmailOrPhone(email, phone))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<Object> updateUser(
            @PathVariable("id") Long id, @RequestBody UserRequest user) {
        userService.updateUser(id, user);
        return ApiResponse.builder()
                .code(HttpStatus.OK.value())
                .message("User updated successfully")
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Object> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ApiResponse.builder()
                .code(HttpStatus.OK.value())
                .message("User deleted successfully")
                .build();
    }
}
