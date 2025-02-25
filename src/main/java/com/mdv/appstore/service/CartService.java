package com.mdv.appstore.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.mdv.appstore.config.CustomUserDetailsService;
import com.mdv.appstore.exception.DataNotFoundException;
import com.mdv.appstore.exception.InsufficientStockException;
import com.mdv.appstore.mapper.CartItemMapper;
import com.mdv.appstore.model.dto.CartItemDTO;
import com.mdv.appstore.model.dto.ProductDTO;
import com.mdv.appstore.model.request.CartItemRequest;
import com.mdv.appstore.model.request.CartItemUpdate;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {

    private final CartItemMapper cartItemMapper;
    private final ProductService productService;
    private final CustomUserDetailsService customUserDetailsService;

    public List<CartItemDTO> getUserCart(Long userId) {
        return cartItemMapper.findByUserId(userId);
    }

    @Transactional
    public void addToCart(CartItemRequest request) {
        Long userId = customUserDetailsService.getCurrentUserId();
        request.setUserId(userId);

        validateProduct(request.getProductId(), request.getQuantity());

        CartItemDTO existingItem =
                cartItemMapper.findByUserIdAndProductId(userId, request.getProductId());

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

    private ProductDTO validateProduct(Long productId, int quantity) {
        ProductDTO product = productService.findById(productId);
        if (product == null) {
            log.error("Product not found: {}", productId);
            throw new DataNotFoundException("Product not found");
        }
        Long totalQuantityInStock = product.getTotalQuantity();

        if (totalQuantityInStock < quantity) {
            log.error(
                    "Insufficient stock. Available: {}, Requested: {}",
                    totalQuantityInStock,
                    quantity);
            throw new InsufficientStockException(
                    "Insufficient stock. Available: "
                            + totalQuantityInStock
                            + ", Requested: "
                            + quantity);
        }
        return product;
    }

    @Transactional
    public void updateQuantity(Long cartItemId, CartItemUpdate cartItemUpdate) {
        int newQuantity = cartItemUpdate.getQuantity();
        Long userId = customUserDetailsService.getCurrentUserId();

        CartItemDTO item = findCartItemByIdAndUserId(cartItemId, userId);

        ProductDTO product = validateProduct(item.getProduct().getId(), newQuantity);

        if (product.getTotalQuantity() < newQuantity) {
            log.error(
                    "Insufficient stock for update. Available: {}, Requested: {}",
                    product.getTotalQuantity(),
                    newQuantity);
            throw new InsufficientStockException(
                    "Insufficient stock. Available: "
                            + product.getTotalQuantity()
                            + ", Requested: "
                            + newQuantity);
        }
        cartItemMapper.updateQuantity(cartItemId, newQuantity);
        log.info(
                "Updated cart item quantity: cartItemId={}, userId={}, newQuantity={}",
                cartItemId,
                userId,
                newQuantity);
    }

    private CartItemDTO findCartItemByIdAndUserId(Long cartItemId, Long userId) {
        CartItemDTO item = cartItemMapper.findByIdAndUserId(cartItemId, userId);
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
