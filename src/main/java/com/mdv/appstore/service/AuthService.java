package com.mdv.appstore.service;

import java.util.Map;

import com.mdv.appstore.dto.request.UserLoginRequest;
import com.mdv.appstore.dto.request.UserRegisterRequest;
import com.mdv.appstore.dto.response.LoginResponse;

public interface AuthService {

    LoginResponse login(UserLoginRequest request);

    void register(UserRegisterRequest user);

    void logout(String accessToken, Map<String, String> body);
}
