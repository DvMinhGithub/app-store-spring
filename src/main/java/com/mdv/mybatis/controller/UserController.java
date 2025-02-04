package com.mdv.mybatis.controller;

import com.mdv.mybatis.model.dto.UserDTO;
import com.mdv.mybatis.model.request.UserCreateRequest;
import com.mdv.mybatis.model.request.UserLoginRequest;
import com.mdv.mybatis.model.request.UserUpdateRequest;
import com.mdv.mybatis.model.response.ApiResponse;
import com.mdv.mybatis.service.UserService;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
    public ApiResponse<Void> createUser(@RequestBody @Valid UserCreateRequest user) {
        userService.createUser(user);
        return ApiResponse.success("User created successfully");
    }

    @PostMapping("/login")
    public ApiResponse<UserDTO> login(@RequestBody @Valid UserLoginRequest user) {
        return ApiResponse.success(userService.login(user), "User logged in successfully");
    }

    @GetMapping
    public ApiResponse<List<UserDTO>> findAll() {
        return ApiResponse.success(userService.findAll(), "Users fetched successfully");
    }

    @GetMapping({"/search"})
    public ApiResponse<UserDTO> findByEmailOrPhone(
            @RequestParam("email") @Nullable @Email(message = "Invalid email format") String email,
            @RequestParam("phone")
                    @Nullable
                    @Pattern(regexp = "\\d{10}", message = "Phone must be 10 digits")
                    String phone) {

        if ((email == null || email.isEmpty()) && (phone == null || phone.isEmpty())) {
            throw new IllegalArgumentException("At least one of email or phone must be provided");
        }
        return ApiResponse.success(
                userService.findByEmailOrPhone(email, phone), "User fetched successfully");
    }

    @PutMapping("/{id}")
    public ApiResponse<Void> updateUser(
            @PathVariable("id") Long id, @RequestBody @Valid UserUpdateRequest user) {
        userService.updateUser(id, user);
        return ApiResponse.success("User updated successfully");
    }

    @PostMapping("/{id}/roles")
    public ApiResponse<Void> assignRoles(
            @PathVariable("id") Long userId, @RequestBody List<Long> roleIds) {
        userService.assignRoles(userId, roleIds);
        return ApiResponse.success("Roles assigned successfully");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ApiResponse.success("User deleted successfully");
    }
}
