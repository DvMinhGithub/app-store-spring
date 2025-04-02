package com.mdv.appstore.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.mdv.appstore.dto.response.RoleResponse;

@Mapper
public interface RoleMapper {
    RoleResponse findByName(String name);

    void save(RoleResponse role);
}
