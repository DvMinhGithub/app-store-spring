package com.mdv.mybatis.service;

import com.mdv.mybatis.exception.DataNotFoundException;
import com.mdv.mybatis.mapper.UserMapper;
import com.mdv.mybatis.model.dto.UserDTO;
import com.mdv.mybatis.model.request.LoginRequest;
import com.mdv.mybatis.model.request.UserRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;

    public void createUser(UserRequest user) {
        userMapper.createUser(user);
    }

    public UserDTO login(LoginRequest user) {
        return userMapper.login(user);
    }

    public List<UserDTO> findAll() {
        return userMapper.findAll();
    }

    public void updateUser(Long id, UserRequest user) {
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
}
