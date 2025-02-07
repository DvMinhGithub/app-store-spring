package com.mdv.appstore.mapper;

import com.mdv.appstore.model.dto.UserDTO;
import com.mdv.appstore.model.request.UserLoginRequest;
import com.mdv.appstore.model.request.UserRegisterRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AuthMapper {
    UserDTO login(@Param("u") UserLoginRequest user);

    void register(@Param("u") UserRegisterRequest user);
}
