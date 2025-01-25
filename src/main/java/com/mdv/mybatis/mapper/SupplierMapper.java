package com.mdv.mybatis.mapper;

import com.mdv.mybatis.model.dto.SupplierDTO;
import com.mdv.mybatis.model.request.SupplierRequest;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SupplierMapper {
    List<SupplierDTO> findAll();

    SupplierDTO findById(@Param("id") Long id);

    void create(@Param("sr") SupplierRequest supplier);

    void update(@Param("sr") SupplierRequest supplier);

    void deleteById(@Param("id") Long id);
}
