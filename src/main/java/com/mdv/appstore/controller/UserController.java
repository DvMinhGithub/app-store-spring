package com.mdv.appstore.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;

import com.mdv.appstore.model.dto.UserDTO;
import com.mdv.appstore.model.request.UserUpdateRequest;
import com.mdv.appstore.model.response.ApiResponse;
import com.mdv.appstore.service.UserService;

@RestController
@RequestMapping("${app.api.base-url}/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

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
