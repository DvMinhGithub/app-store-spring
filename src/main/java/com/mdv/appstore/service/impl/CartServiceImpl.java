package com.mdv.appstore.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mdv.appstore.dto.request.CartItemRequest;
import com.mdv.appstore.dto.request.CartItemUpdate;
import com.mdv.appstore.dto.response.CartItemResponse;
import com.mdv.appstore.dto.response.ProductResponse;
import com.mdv.appstore.exception.DataNotFoundException;
import com.mdv.appstore.exception.InsufficientStockException;
import com.mdv.appstore.mapper.CartItemMapper;
import com.mdv.appstore.security.user.CustomUserDetailsService;
import com.mdv.appstore.service.CartService;
import com.mdv.appstore.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService {

    private final CartItemMapper cartItemMapper;
    private final ProductService productService;
    private final CustomUserDetailsService customUserDetailsService;

    public List<CartItemResponse> getUserCart(Long userId) {
        return cartItemMapper.findByUserId(userId);
    }

    @Transactional
    public void addToCart(CartItemRequest request) {
        Long userId = customUserDetailsService.getCurrentUserId();
        request.setUserId(userId);

        validateProduct(request.getProductId(), request.getQuantity());

        CartItemResponse existingItem = cartItemMapper.findByUserIdAndProductId(userId, request.getProductId());

        if (existingItem != null) {
            int newQuantity = existingItem.getQuantity() + request.getQuantity();
            cartItemMapper.updateQuantity(existingItem.getId(), newQuantity);
            log.info(
                    "Updated quantity for cart item: productId={}, userId={}, newQuantity={}",
                    request.getProductId(),
                    userId,
                    newQuantity);
        } else {
            cartItemMapper.insert(request);
            log.info(
                    "Added new item to cart: productId={}, userId={}, quantity={}",
                    request.getProductId(),
                    userId,
                    request.getQuantity());
        }
    }

    private ProductResponse validateProduct(Long productId, int quantity) {
        ProductResponse product = productService.findById(productId);
        if (product == null) {
            log.error("Product not found: {}", productId);
            throw new DataNotFoundException("Product not found");
        }
        Long totalQuantityInStock = product.getTotalQuantity();

        if (totalQuantityInStock < quantity) {
            log.error("Insufficient stock. Available: {}, Requested: {}", totalQuantityInStock, quantity);
            throw new InsufficientStockException(
                    "Insufficient stock. Available: " + totalQuantityInStock + ", Requested: " + quantity);
        }
        return product;
    }

    @Transactional
    public void updateQuantity(Long cartItemId, CartItemUpdate cartItemUpdate) {
        int newQuantity = cartItemUpdate.getQuantity();
        Long userId = customUserDetailsService.getCurrentUserId();

        CartItemResponse item = findCartItemByIdAndUserId(cartItemId, userId);

        ProductResponse product = validateProduct(item.getProduct().getId(), newQuantity);

        if (product.getTotalQuantity() < newQuantity) {
            log.error(
                    "Insufficient stock for update. Available: {}, Requested: {}",
                    product.getTotalQuantity(),
                    newQuantity);
            throw new InsufficientStockException(
                    "Insufficient stock. Available: " + product.getTotalQuantity() + ", Requested: " + newQuantity);
        }
        cartItemMapper.updateQuantity(cartItemId, newQuantity);
        log.info(
                "Updated cart item quantity: cartItemId={}, userId={}, newQuantity={}",
                cartItemId,
                userId,
                newQuantity);
    }

    private CartItemResponse findCartItemByIdAndUserId(Long cartItemId, Long userId) {
        CartItemResponse item = cartItemMapper.findByIdAndUserId(cartItemId, userId);
        if (item == null) {
            log.error("Cart item not found with id: {} and user id: {}", cartItemId, userId);
            throw new DataNotFoundException("Cart item not found");
        }
        return item;
    }

    @Transactional
    public void removeFromCart(Long cartItemId) {
        Long userId = customUserDetailsService.getCurrentUserId();

        findCartItemByIdAndUserId(cartItemId, userId);
        cartItemMapper.deleteByCartItemIdAndUserId(cartItemId, userId);
        log.info("Removed cart item: cartItemId={}, userId={}", cartItemId, userId);
    }
}
