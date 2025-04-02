package com.mdv.appstore.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.mdv.appstore.dto.request.BrandRequest;
import com.mdv.appstore.dto.response.BrandResponse;
import com.mdv.appstore.exception.DataNotFoundException;
import com.mdv.appstore.mapper.BrandMapper;
import com.mdv.appstore.service.BrandService;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {
    private static final String BRAND_NOT_EXISTS = "Brand not exists";
    private static final String BRAND_ALREADY_EXISTS = "Brand already exists";
    private final BrandMapper brandMapper;

    @Override
    public void createBrand(BrandRequest brand) {
        if (brandMapper.existsByName(brand.getName())) {
            throw new IllegalArgumentException(BRAND_ALREADY_EXISTS);
        }
        brandMapper.createBrand(brand);
    }

    @Override
    public List<BrandResponse> findAll() {
        return brandMapper.findAll();
    }

    @Override
    public BrandResponse findById(Long id) {
        BrandResponse brand = brandMapper.findById(id);
        if (brand == null) {
            throw new DataNotFoundException(BRAND_NOT_EXISTS);
        }
        return brand;
    }

    @Override
    public void updateBrand(Long id, BrandRequest brand) {
        if (findById(id) == null) {
            throw new DataNotFoundException(BRAND_NOT_EXISTS);
        }

        if (brandMapper.existsByName(brand.getName())) {
            throw new IllegalArgumentException(BRAND_ALREADY_EXISTS);
        }
        brandMapper.updateBrand(id, brand);
    }

    @Override
    public void deleteBrand(Long id) {
        BrandResponse brand = findById(id);
        if (brand == null) {
            throw new DataNotFoundException(BRAND_NOT_EXISTS);
        }
        brandMapper.deleteBrand(id);
    }
}
