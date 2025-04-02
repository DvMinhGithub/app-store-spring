package com.mdv.appstore.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mdv.appstore.dto.request.UserLoginRequest;
import com.mdv.appstore.dto.request.UserRegisterRequest;
import com.mdv.appstore.dto.response.UserResponse;

@Mapper
public interface AuthMapper {
    UserResponse login(@Param("u") UserLoginRequest user);

    void register(@Param("u") UserRegisterRequest user);
}
