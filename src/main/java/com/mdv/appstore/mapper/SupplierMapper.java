package com.mdv.appstore.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mdv.appstore.dto.request.SupplierRequest;
import com.mdv.appstore.dto.response.SupplierResponse;

@Mapper
public interface SupplierMapper {
    List<SupplierResponse> findAll();

    SupplierResponse findById(@Param("id") Long id);

    void create(@Param("sr") SupplierRequest supplier);

    void update(@Param("sr") SupplierRequest supplier);

    void deleteById(@Param("id") Long id);
}
