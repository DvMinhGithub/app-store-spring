package com.mdv.appstore.service;

import com.mdv.appstore.config.JwtUtils;
import com.mdv.appstore.enums.UserRole;
import com.mdv.appstore.mapper.AuthMapper;
import com.mdv.appstore.mapper.RoleMapper;
import com.mdv.appstore.mapper.UserMapper;
import com.mdv.appstore.model.dto.LoginDTO;
import com.mdv.appstore.model.dto.RoleDTO;
import com.mdv.appstore.model.request.UserLoginRequest;
import com.mdv.appstore.model.request.UserRegisterRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthMapper authMapper;
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public LoginDTO login(UserLoginRequest request) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String generateAccessToken = jwtUtils.generateAccessToken(userDetails);
        String generateRefreshToken = jwtUtils.generateRefreshToken(userDetails);

        return LoginDTO.builder()
                .accessToken(generateAccessToken)
                .refreshToken(generateRefreshToken)
                .build();
    }

    @Transactional
    public void register(UserRegisterRequest user) {
        if (userMapper.findByEmailOrPhone(user.getEmail(), null) != null) {
            throw new IllegalArgumentException("Email already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        authMapper.register(user);

        RoleDTO role = roleMapper.findByName(UserRole.CUSTOMER.name());
        userService.assignRoles(user.getId(), List.of(role.getId()));
    }

    public void logout() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'logout'");
    }
}
