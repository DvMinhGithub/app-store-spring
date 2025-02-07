package com.mdv.appstore.service;

import com.mdv.appstore.mapper.AuthMapper;
import com.mdv.appstore.model.dto.LoginDTO;
import com.mdv.appstore.model.dto.UserDTO;
import com.mdv.appstore.model.request.UserLoginRequest;
import com.mdv.appstore.model.request.UserRegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthMapper authMapper;

    public LoginDTO login(UserLoginRequest request) {
        UserDTO userDTO = authMapper.login(request);
        if (userDTO == null) {
            throw new RuntimeException("Invalid email or password");
        }
        return null;
    }

    public void register(UserRegisterRequest user) {
        authMapper.register(user);
    }

    public void logout() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'logout'");
    }
}
