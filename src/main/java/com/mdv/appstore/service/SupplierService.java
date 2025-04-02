package com.mdv.appstore.service;

import java.util.List;

import com.mdv.appstore.dto.request.SupplierRequest;
import com.mdv.appstore.dto.response.SupplierResponse;

public interface SupplierService {

    List<SupplierResponse> findAll();

    SupplierResponse findById(Long id);

    void create(SupplierRequest supplier);

    void update(Long id, SupplierRequest supplier);

    void deleteById(Long id);
}
