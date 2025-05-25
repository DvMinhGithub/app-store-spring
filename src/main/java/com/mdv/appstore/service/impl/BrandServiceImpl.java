package com.mdv.appstore.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mdv.appstore.dto.request.BrandRequest;
import com.mdv.appstore.dto.request.PaginationRequest;
import com.mdv.appstore.dto.response.BrandResponse;
import com.mdv.appstore.dto.response.PaginationResponse;
import com.mdv.appstore.exception.DataNotFoundException;
import com.mdv.appstore.mapper.BrandMapper;
import com.mdv.appstore.service.BrandService;

import lombok.RequiredArgsConstructor;

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
    public PaginationResponse<BrandResponse> findAll(PaginationRequest request) {
        int offset = (request.getPage() - 1) * request.getSize();
        List<BrandResponse> brands = brandMapper.findAllWithPagination(offset, request.getSize(), request.getSortBy(),
                request.getSortDirection());
        long total = brandMapper.countAll();
        return PaginationResponse.of(brands, total, request.getPage(), request.getSize());
    }

    @Override
    public BrandResponse findById(Long id) {
        BrandResponse brand = brandMapper.findById(id);
        if (brand == null || brand.getDeletedAt() != null) {
            throw new DataNotFoundException(BRAND_NOT_EXISTS);
        }
        return brand;
    }

    @Override
    public List<BrandResponse> findByName(String name) {
        return brandMapper.findByName(name);
    }

    @Override
    public void updateBrand(Long id, BrandRequest brand) {
        BrandResponse old = findById(id);
        if (old == null) {
            throw new DataNotFoundException(BRAND_NOT_EXISTS);
        }
        if (!old.getName().equals(brand.getName()) && brandMapper.existsByName(brand.getName())) {
            throw new IllegalArgumentException(BRAND_ALREADY_EXISTS);
        }
        brandMapper.updateBrand(id, brand);
    }

    @Override
    public void softDeleteBrand(Long id) {
        BrandResponse brand = findById(id);
        if (brand == null) {
            throw new DataNotFoundException(BRAND_NOT_EXISTS);
        }
        brandMapper.softDeleteBrand(id, java.time.LocalDateTime.now());
    }

    @Override
    public void restoreBrand(Long id) {
        BrandResponse brand = brandMapper.findById(id);
        if (brand == null) {
            throw new DataNotFoundException(BRAND_NOT_EXISTS);
        }
        brandMapper.restoreBrand(id);
    }
}
