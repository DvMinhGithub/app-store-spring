package com.mdv.appstore.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.mdv.appstore.exception.DataNotFoundException;
import com.mdv.appstore.mapper.SupplierMapper;
import com.mdv.appstore.model.dto.SupplierDTO;
import com.mdv.appstore.model.request.SupplierRequest;

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
