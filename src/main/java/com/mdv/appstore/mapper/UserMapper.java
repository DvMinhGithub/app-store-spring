package com.mdv.appstore.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mdv.appstore.dto.request.UserUpdateRequest;
import com.mdv.appstore.dto.response.UserResponse;

@Mapper
public interface UserMapper {
    List<UserResponse> findAll();

    UserResponse findById(@Param("id") Long id);

    UserResponse findByEmailOrPhone(@Param("email") String email, @Param("phone") String phone);

    void updateUser(@Param("id") Long id, @Param("u") UserUpdateRequest user);

    void updatePassword(@Param("id") Long id, @Param("password") String password);

    void deleteUser(@Param("id") Long id);

    void assignRoles(@Param("id") Long userId, @Param("roleIds") List<Long> roleIds);

    void deleteRolesByUserId(@Param("userId") Long userId);

    void insertRoles(@Param("userId") Long userId, @Param("roleIds") List<Long> roleIds);
}
