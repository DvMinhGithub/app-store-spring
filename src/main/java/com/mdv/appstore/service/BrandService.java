package com.mdv.appstore.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.mdv.appstore.exception.DataNotFoundException;
import com.mdv.appstore.mapper.BrandMapper;
import com.mdv.appstore.model.dto.BrandDTO;
import com.mdv.appstore.model.request.BrandRequest;

@Service
@RequiredArgsConstructor
public class BrandService {
    private static final String BRAND_NOT_EXISTS = "Brand not exists";
    private static final String BRAND_ALREADY_EXISTS = "Brand already exists";
    private final BrandMapper brandMapper;

    public void createBrand(BrandRequest brand) {
        if (brandMapper.existsByName(brand.getName())) {
            throw new IllegalArgumentException(BRAND_ALREADY_EXISTS);
        }
        brandMapper.createBrand(brand);
    }

    public List<BrandDTO> findAll() {
        return brandMapper.findAll();
    }

    public BrandDTO findById(Long id) {
        BrandDTO brand = brandMapper.findById(id);
        if (brand == null) {
            throw new DataNotFoundException(BRAND_NOT_EXISTS);
        }
        return brand;
    }

    public void updateBrand(Long id, BrandRequest brand) {
        if (findById(id) == null) {
            throw new DataNotFoundException(BRAND_NOT_EXISTS);
        }

        if (brandMapper.existsByName(brand.getName())) {
            throw new IllegalArgumentException(BRAND_ALREADY_EXISTS);
        }
        brandMapper.updateBrand(id, brand);
    }

    public void deleteBrand(Long id) {
        BrandDTO brand = findById(id);
        if (brand == null) {
            throw new DataNotFoundException(BRAND_NOT_EXISTS);
        }
        brandMapper.deleteBrand(id);
    }
}
