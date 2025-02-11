package com.mdv.appstore.mapper;

import com.mdv.appstore.model.dto.CartItemDTO;
import com.mdv.appstore.model.request.CartItemRequest;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CartItemMapper {
    List<CartItemDTO> findByUserId(Long userId);

    CartItemDTO findById(Long id);

    int insert(CartItemRequest cartItem);

    int updateQuantity(@Param("id") Long id, @Param("quantity") Integer quantity);

    int delete(Long id);

    CartItemDTO findByUserIdAndProductId(
            @Param("userId") Long userId, @Param("productId") Long productId);
}
