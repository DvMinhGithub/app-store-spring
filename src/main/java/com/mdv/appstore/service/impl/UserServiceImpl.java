package com.mdv.appstore.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.mdv.appstore.dto.request.UserUpdateRequest;
import com.mdv.appstore.dto.response.UserResponse;
import com.mdv.appstore.exception.DataNotFoundException;
import com.mdv.appstore.mapper.UserMapper;
import com.mdv.appstore.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;

    public List<UserResponse> findAll() {
        return userMapper.findAll();
    }

    public void updateUser(Long id, UserUpdateRequest user) {
        userMapper.updateUser(id, user);
    }

    public void deleteUser(Long id) {
        userMapper.deleteUser(id);
    }

    public UserResponse findByEmailOrPhone(String email, String phone) {
        UserResponse user = userMapper.findByEmailOrPhone(email, phone);
        if (user == null) {
            throw new DataNotFoundException("User not exist");
        }
        return user;
    }

    public void assignRoles(Long userId, List<Long> roleIds) {
        if (roleIds == null || roleIds.isEmpty()) {
            throw new IllegalArgumentException("At least one role must be provided");
        }
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        userMapper.deleteRolesByUserId(userId);
        userMapper.insertRoles(userId, roleIds);
    }
}
