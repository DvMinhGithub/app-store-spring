package com.mdv.mybatis.mapper;

import com.mdv.mybatis.model.dto.UserDTO;
import com.mdv.mybatis.model.request.LoginRequest;
import com.mdv.mybatis.model.request.UserRequest;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    void createUser(@Param("user") UserRequest user);

    UserDTO login(@Param("user") LoginRequest user);

    List<UserDTO> findAll();

    UserDTO findById(@Param("id") Long id);

    UserDTO findByEmailOrPhone(@Param("email") String email, @Param("phone") String phone);

    void updateUser(@Param("id") Long id, @Param("user") UserRequest user);

    void updatePassword(@Param("id") Long id, @Param("password") String password);

    void deleteUser(@Param("id") Long id);
}
