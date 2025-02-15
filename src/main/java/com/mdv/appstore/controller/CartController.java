package com.mdv.appstore.controller;

import com.mdv.appstore.model.dto.CartItemDTO;
import com.mdv.appstore.model.request.CartItemRequest;
import com.mdv.appstore.model.request.CartItemUpdate;
import com.mdv.appstore.model.response.ApiResponse;
import com.mdv.appstore.service.CartService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${app.api.base-url}/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping("/{userId}")
    public ApiResponse<List<CartItemDTO>> getCart(@PathVariable("userId") Long userId) {
        return ApiResponse.success(cartService.getUserCart(userId), "Cart retrieved successfully");
    }

    @PostMapping
    public ApiResponse<Void> addToCart(@RequestBody @Valid CartItemRequest request) {
        cartService.addToCart(request);
        return ApiResponse.success("Item added to cart successfully");
    }

    @PutMapping("/{cartItemId}")
    public ApiResponse<Void> updateQuantity(
            @PathVariable("cartItemId") Long cartItemId,
            @RequestBody @Valid CartItemUpdate cartItemUpdate) {
        cartService.updateQuantity(cartItemId, cartItemUpdate);
        return ApiResponse.success("Quantity updated successfully");
    }

    @DeleteMapping("/{cartItemId}")
    public ApiResponse<Void> removeFromCart(@PathVariable("cartItemId") Long cartItemId) {
        cartService.removeFromCart(cartItemId);
        return ApiResponse.success("Item removed from cart successfully");
    }
}
