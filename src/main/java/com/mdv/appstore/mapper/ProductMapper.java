package com.mdv.appstore.mapper;

import com.mdv.appstore.model.dto.ProductCreateDTO;
import com.mdv.appstore.model.dto.ProductDTO;
import com.mdv.appstore.model.request.ProductRequest;
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

    void updateTotalQuantity(@Param("id") Long id, @Param("totalQuantity") Long totalQuantity);

    void increaseSoldQuantity(@Param("id") Long id, @Param("quantity") Long quantity);

    void decreaseSoldQuantity(@Param("id") Long id, @Param("quantity") Long quantity);

    void incrementViewCount(@Param("id") Long id);

    void softDelete(@Param("id") Long id);

    void restore(@Param("id") Long id);
}
