package com.mdv.appstore.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mdv.appstore.dto.request.CartItemRequest;
import com.mdv.appstore.dto.response.CartItemResponse;

@Mapper
public interface CartItemMapper {

    List<CartItemResponse> findByUserId(@Param("userId") Long userId);

    List<CartItemResponse> findAllByIdsAndUserId(@Param("listId") List<Long> listId, @Param("userId") Long userId);

    CartItemResponse findByUserIdAndProductId(@Param("userId") Long userId, @Param("productId") Long productId);

    CartItemResponse findByIdAndUserId(@Param("id") Long id, @Param("userId") Long userId);

    CartItemResponse findById(Long id);

    int insert(CartItemRequest cartItem);

    int updateQuantity(@Param("id") Long id, @Param("quantity") Integer quantity);

    int deleteByCartItemIdAndUserId(@Param("id") Long id, @Param("userId") Long userId);
}
