package com.mdv.appstore.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mdv.appstore.model.dto.ProductAttributeDTO;

@Mapper
public interface ProductAtrributeMapper {
    void create(@Param("pad") ProductAttributeDTO productAtrributeDTO);

    ProductAttributeDTO findById(Long id);

    List<ProductAttributeDTO> findAll();

    List<ProductAttributeDTO> findAttributesByProductId(Long productId);

    void update(Long id, @Param("pad") ProductAttributeDTO productAtrributeDTO);

    void delete(Long id);
}
