package com.mdv.appstore.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.mdv.appstore.model.dto.RoleDTO;

@Mapper
public interface RoleMapper {
    RoleDTO findByName(String name);

    void save(RoleDTO role);
}
