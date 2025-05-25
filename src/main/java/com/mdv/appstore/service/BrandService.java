package com.mdv.appstore.service;

import java.util.List;

import com.mdv.appstore.dto.request.BrandRequest;
import com.mdv.appstore.dto.request.PaginationRequest;
import com.mdv.appstore.dto.response.BrandResponse;
import com.mdv.appstore.dto.response.PaginationResponse;

public interface BrandService {

    void createBrand(BrandRequest brand);

    PaginationResponse<BrandResponse> findAll(PaginationRequest request);

    BrandResponse findById(Long id);

    List<BrandResponse> findByName(String name);

    void updateBrand(Long id, BrandRequest brand);

    void softDeleteBrand(Long id);

    void restoreBrand(Long id);
}
