package com.mdv.appstore.service;

import java.util.List;

import com.mdv.appstore.dto.request.CartItemRequest;
import com.mdv.appstore.dto.request.CartItemUpdate;
import com.mdv.appstore.dto.response.CartItemResponse;

public interface CartService {

    List<CartItemResponse> getUserCart(Long userId);

    void addToCart(CartItemRequest request);

    void updateQuantity(Long cartItemId, CartItemUpdate cartItemUpdate);

    void removeFromCart(Long cartItemId);
}
