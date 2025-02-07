package com.mdv.appstore.mapper;

import com.mdv.appstore.model.dto.SupplierDTO;
import com.mdv.appstore.model.request.SupplierRequest;
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
