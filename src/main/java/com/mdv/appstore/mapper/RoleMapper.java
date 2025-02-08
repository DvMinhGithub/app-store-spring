package com.mdv.appstore.mapper;

import com.mdv.appstore.model.dto.RoleDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleMapper {
    RoleDTO findByName(String name);

    void save(RoleDTO role);
}
