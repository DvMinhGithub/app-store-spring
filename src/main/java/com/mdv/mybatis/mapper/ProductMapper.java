package com.mdv.mybatis.mapper;

import com.mdv.mybatis.model.dto.ProductCreateDTO;
import com.mdv.mybatis.model.dto.ProductDTO;
import com.mdv.mybatis.model.request.ProductRequest;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProductMapper {
    void createProduct(@Param("product") ProductCreateDTO product);

    List<ProductDTO> findAll();

    ProductDTO findByName(@Param("name") String name);

    ProductDTO findById(@Param("id") Long id);

    void updateProduct(@Param("id") Long id, @Param("product") ProductRequest product);

    void softDelete(@Param("id") Long id);

    void restore    (@Param("id") Long id);
}
