package com.mdv.appstore.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mdv.appstore.dto.request.ProductRequest;
import com.mdv.appstore.dto.response.ProductCreateResponse;
import com.mdv.appstore.dto.response.ProductResponse;

@Mapper
public interface ProductMapper {
    void createProduct(@Param("product") ProductCreateResponse product);

    List<ProductResponse> findAll();

    ProductResponse findByName(@Param("name") String name);

    ProductResponse findById(@Param("id") Long id);

    void updateProduct(@Param("id") Long id, @Param("product") ProductRequest product);

    void updateTotalQuantity(@Param("id") Long id, @Param("totalQuantity") Long totalQuantity);

    void increaseSoldQuantity(@Param("id") Long id, @Param("quantity") Integer quantity);

    void decreaseSoldQuantity(@Param("id") Long id, @Param("quantity") Integer quantity);

    void incrementViewCount(@Param("id") Long id);

    void softDelete(@Param("id") Long id);

    void restore(@Param("id") Long id);
}
