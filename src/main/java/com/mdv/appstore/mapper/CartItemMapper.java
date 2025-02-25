package com.mdv.appstore.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mdv.appstore.model.dto.CartItemDTO;
import com.mdv.appstore.model.request.CartItemRequest;

@Mapper
public interface CartItemMapper {

    List<CartItemDTO> findByUserId(@Param("userId") Long userId);

    List<CartItemDTO> findAllByIdsAndUserId(
            @Param("listId") List<Long> listId, @Param("userId") Long userId);

    CartItemDTO findByUserIdAndProductId(
            @Param("userId") Long userId, @Param("productId") Long productId);

    CartItemDTO findByIdAndUserId(@Param("id") Long id, @Param("userId") Long userId);

    CartItemDTO findById(Long id);

    int insert(CartItemRequest cartItem);

    int updateQuantity(@Param("id") Long id, @Param("quantity") Integer quantity);

    int deleteByCartItemIdAndUserId(@Param("id") Long id, @Param("userId") Long userId);
}
