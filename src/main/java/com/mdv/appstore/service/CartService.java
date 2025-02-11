package com.mdv.appstore.service;

import com.mdv.appstore.exception.DataNotFoundException;
import com.mdv.appstore.mapper.CartItemMapper;
import com.mdv.appstore.model.dto.CartItemDTO;
import com.mdv.appstore.model.dto.ProductDTO;
import com.mdv.appstore.model.request.CartItemRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {
    private final CartItemMapper cartItemMapper;
    private final ProductService productService;

    public List<CartItemDTO> getUserCart(Long userId) {
        return cartItemMapper.findByUserId(userId);
    }

    @Transactional
    public void addToCart(CartItemRequest request) {
        validateProduct(request);
        CartItemDTO existingItem =
                cartItemMapper.findByUserIdAndProductId(
                        request.getUserId(), request.getProductId());

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + request.getQuantity());
            cartItemMapper.updateQuantity(existingItem.getId(), existingItem.getQuantity());
        } else {
            cartItemMapper.insert(request);
        }
    }

    private void validateProduct(CartItemRequest request) {
        ProductDTO product = productService.findById(request.getProductId());
        if (product == null) {
            log.warn("Product with ID {} not found, cannot add to cart.", request.getProductId());
            throw new DataNotFoundException("Product not found");
        }
        if (product.getTotalQuantity() < request.getQuantity()) {
            throw new RuntimeException(
                    "Insufficient stock. Available: "
                            + product.getTotalQuantity()
                            + ", Requested: "
                            + request.getQuantity());
        }
        if (product.getTotalQuantity() != null
                && request.getQuantity() > product.getTotalQuantity()) {
            log.warn(
                    "Quantity exceeds product maximum order limit: {}", product.getTotalQuantity());
            throw new RuntimeException(
                    "Quantity exceeds product maximum order limit: " + product.getTotalQuantity());
        }
    }

    @Transactional
    public void updateQuantity(Long cartItemId, CartItemRequest request) {
        validateProduct(request);
        CartItemDTO item = cartItemMapper.findById(cartItemId);
        if (item == null) {
            log.warn("Cart item with ID {} not found, cannot update quantity.", cartItemId);
            throw new DataNotFoundException("Cart item not found");
        }
        validateProduct(request);
        if (item != null) {
            item.setQuantity(request.getQuantity());
            cartItemMapper.updateQuantity(cartItemId, request.getQuantity());
        }
    }

    @Transactional
    public void removeFromCart(Long cartItemId) {
        CartItemDTO existingItem = cartItemMapper.findById(cartItemId);
        if (existingItem == null) {
            log.warn("Cart item with ID {} not found, cannot remove.", cartItemId);
            throw new DataNotFoundException("Cart item not found");
        }
        cartItemMapper.delete(cartItemId);
    }
}
