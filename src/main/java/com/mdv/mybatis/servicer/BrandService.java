package com.mdv.mybatis.servicer;

import com.mdv.mybatis.exception.DataNotFoundException;
import com.mdv.mybatis.mapper.BrandMapper;
import com.mdv.mybatis.model.dto.BrandDTO;
import com.mdv.mybatis.model.request.BrandRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BrandService {
    private final BrandMapper brandMapper;
    private static final String BRAND_NOT_EXISTS = "Brand not exists";
    private static final String BRAND_ALREADY_EXISTS = "Brand already exists";

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
