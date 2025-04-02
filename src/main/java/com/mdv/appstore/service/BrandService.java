package com.mdv.appstore.service;

import java.util.List;

import com.mdv.appstore.dto.request.BrandRequest;
import com.mdv.appstore.dto.response.BrandResponse;

public interface BrandService {

    void createBrand(BrandRequest brand);

    List<BrandResponse> findAll();

    BrandResponse findById(Long id);

    void updateBrand(Long id, BrandRequest brand);

    void deleteBrand(Long id);
}
