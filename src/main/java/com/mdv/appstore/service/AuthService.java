package com.mdv.appstore.service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.mdv.appstore.dto.request.UserLoginRequest;
import com.mdv.appstore.dto.request.UserRegisterRequest;
import com.mdv.appstore.dto.response.LoginResponse;
import com.mdv.appstore.dto.response.RoleResponse;
import com.mdv.appstore.dto.response.UserResponse;
import com.mdv.appstore.enums.UserRole;
import com.mdv.appstore.exception.DuplicateEntryException;
import com.mdv.appstore.mapper.AuthMapper;
import com.mdv.appstore.mapper.RoleMapper;
import com.mdv.appstore.mapper.UserMapper;
import com.mdv.appstore.security.jwt.JwtUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private static final String USER_CACHE_KEY_PREFIX = "register:user:emailOrPhone:";
    private final AuthMapper authMapper;
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final RedisService redisService;

    public LoginResponse login(UserLoginRequest request) {
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String generateAccessToken = jwtUtils.generateAccessToken(userDetails);
        String generateRefreshToken = jwtUtils.generateRefreshToken(userDetails);

        return LoginResponse.builder()
                .accessToken(generateAccessToken)
                .refreshToken(generateRefreshToken)
                .build();
    }

    @Transactional
    public void register(UserRegisterRequest user) {
        if (isEmailOrPhoneExistsInCache(user.getEmail(), user.getPhone())) {
            log.warn(
                    "Registration failed: email {} and phone {} already exists",
                    user.getEmail(),
                    user.getPhone());
            throw new DuplicateEntryException("Email or phone already exists");
        }

        UserResponse userResponse = userMapper.findByEmailOrPhone(user.getEmail(), user.getPhone());
        if (userResponse != null) {
            cacheEmailAndPhone(user.getEmail(), user.getPhone());
            log.warn(
                    "Registration failed: email {} and phone {} already exists",
                    user.getEmail(),
                    user.getPhone());
            throw new DuplicateEntryException("Email or phone already exists");
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        authMapper.register(user);

        RoleResponse role = roleMapper.findByName(UserRole.CUSTOMER.name());
        userService.assignRoles(user.getId(), List.of(role.getId()));
    }

    public void logout(String accessToken, Map<String, String> body) {
        Date expirationFromToken = jwtUtils.getExpirationFromToken(accessToken);
        long accessTokenTtl = expirationFromToken.getTime() - Instant.now().toEpochMilli();

        if (accessTokenTtl > 0) {
            String blacklistAccessToken = "blacklist:" + accessToken;
            redisService.setValue(
                    blacklistAccessToken, "logged_out", accessTokenTtl, TimeUnit.MILLISECONDS);
            log.info(
                    "AccessToken has been successfully blacklisted. Redis Key: {}, TTL: {} ms",
                    blacklistAccessToken,
                    accessTokenTtl);
        }

        if (body != null && body.containsKey("refreshToken")) {
            String refreshToken = body.get("refreshToken");
            long refreshTokenTtl =
                    jwtUtils.getExpirationFromToken(refreshToken).getTime()
                            - System.currentTimeMillis();
            String blacklistRefresh = "blacklist:" + refreshToken;
            if (refreshTokenTtl > 0) {
                redisService.setValue(
                        blacklistRefresh, "logged_out", refreshTokenTtl, TimeUnit.MILLISECONDS);
                log.info(
                        "RefreshToken has been successfully blacklisted. Redis Key: {}, TTL: {} ms",
                        blacklistRefresh,
                        refreshTokenTtl);
            }
        }
    }

    private boolean isEmailOrPhoneExistsInCache(String email, String phone) {
        return Boolean.TRUE.equals(redisService.hasKey(USER_CACHE_KEY_PREFIX + email))
                || Boolean.TRUE.equals(redisService.hasKey(USER_CACHE_KEY_PREFIX + phone));
    }

    private void cacheEmailAndPhone(String email, String phone) {
        redisService.setValue(USER_CACHE_KEY_PREFIX + email, "exists", 5, TimeUnit.MINUTES);
        redisService.setValue(USER_CACHE_KEY_PREFIX + phone, "exists", 5, TimeUnit.MINUTES);
    }
}
