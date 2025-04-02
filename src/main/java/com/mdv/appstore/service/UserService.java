package com.mdv.appstore.service;

import java.util.List;

import com.mdv.appstore.dto.request.UserUpdateRequest;
import com.mdv.appstore.dto.response.UserResponse;

public interface UserService {

    List<UserResponse> findAll();

    void updateUser(Long id, UserUpdateRequest user);

    void deleteUser(Long id);

    UserResponse findByEmailOrPhone(String email, String phone);

    void assignRoles(Long userId, List<Long> roleIds);
}
