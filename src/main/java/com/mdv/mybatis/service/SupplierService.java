package com.mdv.mybatis.service;

import com.mdv.mybatis.exception.DataNotFoundException;
import com.mdv.mybatis.mapper.SupplierMapper;
import com.mdv.mybatis.model.dto.SupplierDTO;
import com.mdv.mybatis.model.request.SupplierRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SupplierService {
    private final SupplierMapper supplierMapper;

    public List<SupplierDTO> findAll() {
        return supplierMapper.findAll();
    }

    public SupplierDTO findById(Long id) {
        SupplierDTO supplier = supplierMapper.findById(id);
        if (supplier == null) {
            throw new DataNotFoundException("Supplier not found");
        }
        return supplier;
    }

    public void create(SupplierRequest supplier) {
        supplierMapper.create(supplier);
    }

    public void update(Long id, SupplierRequest supplier) {
        findById(id);
        supplierMapper.update(supplier);
    }

    public void deleteById(Long id) {
        findById(id);
        supplierMapper.deleteById(id);
    }
}
