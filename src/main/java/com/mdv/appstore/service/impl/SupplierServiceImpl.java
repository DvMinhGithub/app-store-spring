package com.mdv.appstore.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.mdv.appstore.dto.request.SupplierRequest;
import com.mdv.appstore.dto.response.SupplierResponse;
import com.mdv.appstore.exception.DataNotFoundException;
import com.mdv.appstore.mapper.SupplierMapper;
import com.mdv.appstore.service.SupplierService;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {
    private final SupplierMapper supplierMapper;

    public List<SupplierResponse> findAll() {
        return supplierMapper.findAll();
    }

    public SupplierResponse findById(Long id) {
        SupplierResponse supplier = supplierMapper.findById(id);
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
