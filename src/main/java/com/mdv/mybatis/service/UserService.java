package com.mdv.mybatis.service;

import com.mdv.mybatis.exception.DataNotFoundException;
import com.mdv.mybatis.mapper.UserMapper;
import com.mdv.mybatis.model.dto.UserDTO;
import com.mdv.mybatis.model.request.UserCreateRequest;
import com.mdv.mybatis.model.request.UserLoginRequest;
import com.mdv.mybatis.model.request.UserUpdateRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;

    public void createUser(UserCreateRequest user) {
        userMapper.createUser(user);
    }

    public UserDTO login(UserLoginRequest user) {
        return userMapper.login(user);
    }

    public List<UserDTO> findAll() {
        return userMapper.findAll();
    }

    public void updateUser(Long id, UserUpdateRequest user) {
        userMapper.updateUser(id, user);
    }

    public void deleteUser(Long id) {
        userMapper.deleteUser(id);
    }

    public UserDTO findByEmailOrPhone(String email, String phone) {
        UserDTO user = userMapper.findByEmailOrPhone(email, phone);
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
        // userMapper.assignRoles(userId, roleIds);
        userMapper.deleteRolesByUserId(userId);
        userMapper.insertRoles(userId, roleIds);
    }
}
