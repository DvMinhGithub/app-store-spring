package com.mdv.appstore.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mdv.appstore.dto.request.OrderCreateRequest;
import com.mdv.appstore.dto.request.OrderHistoryRequest;
import com.mdv.appstore.dto.request.OrderItemRequest;
import com.mdv.appstore.dto.request.OrderStatusRequest;
import com.mdv.appstore.dto.response.CartItemResponse;
import com.mdv.appstore.dto.response.OrderHistoryResponse;
import com.mdv.appstore.dto.response.OrderItemResponse;
import com.mdv.appstore.dto.response.OrderResponse;
import com.mdv.appstore.dto.response.RevenueResponse;
import com.mdv.appstore.dto.response.VoucherResponse;
import com.mdv.appstore.enums.OrderStatus;
import com.mdv.appstore.exception.CartEmptyException;
import com.mdv.appstore.exception.DataNotFoundException;
import com.mdv.appstore.exception.DiscountGreaterThanTotalPriceException;
import com.mdv.appstore.exception.InvalidOrderStatusException;
import com.mdv.appstore.exception.TotalPriceLessThanConditionException;
import com.mdv.appstore.exception.VoucherNotActiveException;
import com.mdv.appstore.mapper.CartItemMapper;
import com.mdv.appstore.mapper.OrderMapper;
import com.mdv.appstore.mapper.ProductMapper;
import com.mdv.appstore.mapper.RevenueMapper;
import com.mdv.appstore.mapper.UserMapper;
import com.mdv.appstore.mapper.VoucherMapper;
import com.mdv.appstore.security.user.CustomUserDetailsService;
import com.mdv.appstore.service.OrderService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;
    private final UserMapper userMapper;
    private final CartItemMapper cartItemMapper;
    private final ProductMapper productMapper;
    private final VoucherMapper voucherMapper;
    private final RevenueMapper revenueMapper;
    private final CustomUserDetailsService customUserDetailsService;

    public OrderResponse getOrderById(Long id) {
        OrderResponse order = orderMapper.findById(id);
        if (order == null) {
            throw new DataNotFoundException(String.format("Order with ID %d not found", id));
        }
        return order;
    }

    @Transactional
    public void createOrder(OrderCreateRequest orderCreateRequest) {
        Long userId = customUserDetailsService.getCurrentUserId();
        orderCreateRequest.setUserId(userId);
        orderCreateRequest.setOrderCode(generateOrderCode());

        List<CartItemResponse> cartItems =
                cartItemMapper.findAllByIdsAndUserId(orderCreateRequest.getCartItemIds(), userId);
        if (cartItems.isEmpty()) {
            throw new CartEmptyException("Cart is empty");
        }

        double totalPriceOrder = 0;
        for (CartItemResponse cartItem : cartItems) {
            totalPriceOrder += cartItem.getProduct().getPrice() * cartItem.getQuantity();
        }

        VoucherResponse voucher = voucherMapper.selectVoucherByCode(orderCreateRequest.getVoucherCode());

        if (voucher != null) {
            Boolean isVoucherActive = voucher.getIsActive();
            if (Boolean.FALSE.equals(isVoucherActive)) {
                throw new VoucherNotActiveException("Voucher is not active");
            }

            if (voucher.getConditionValue() > totalPriceOrder) {
                throw new TotalPriceLessThanConditionException("Total price is less than minimum total price");
            }

            if (voucher.getDiscountPrice() > totalPriceOrder) {
                throw new DiscountGreaterThanTotalPriceException("Discount is greater than total price");
            }

            voucherMapper.updateUsedQuantity(voucher.getId());
            totalPriceOrder -= voucher.getDiscountPrice();
        } else {
            throw new DataNotFoundException(
                    String.format("Voucher with code %s not found", orderCreateRequest.getVoucherCode()));
        }

        orderCreateRequest.setTotalPrice(totalPriceOrder);
        orderCreateRequest.setStatus(OrderStatus.PENDING.name());
        orderMapper.insertOrder(orderCreateRequest);

        saveOrderDetailsAndProcessCartItems(orderCreateRequest, cartItems);
    }

    private void saveOrderDetailsAndProcessCartItems(
            OrderCreateRequest orderCreateRequest, List<CartItemResponse> cartItems) {
        for (CartItemResponse cartItem : cartItems) {
            OrderItemRequest orderItemRequest = OrderItemRequest.builder()
                    .orderId(orderCreateRequest.getId())
                    .productId(cartItem.getProduct().getId())
                    .quantity(cartItem.getQuantity())
                    .priceAtOrderTime(cartItem.getProduct().getPrice())
                    .build();
            orderMapper.insertOrderDetail(orderItemRequest);

            cartItemMapper.deleteByCartItemIdAndUserId(cartItem.getId(), customUserDetailsService.getCurrentUserId());

            Long newTotalQuantity = cartItem.getProduct().getTotalQuantity() - cartItem.getQuantity();
            productMapper.updateTotalQuantity(cartItem.getProduct().getId(), newTotalQuantity);
            productMapper.increaseSoldQuantity(cartItem.getProduct().getId(), cartItem.getQuantity());
        }
    }

    private String generateOrderCode() {
        return "ORDER-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public List<OrderResponse> getOrdersByUserId(Long userId) {
        if (userMapper.findById(userId) == null) {
            throw new DataNotFoundException(String.format("User with ID %d not found", userId));
        }
        return orderMapper.findAllByUserId(userId);
    }

    @Transactional
    public void updateOrderStatus(Long id, OrderStatusRequest orderStatusRequest) {
        OrderResponse order = getOrderById(id);
        String status = orderStatusRequest.getStatus().toUpperCase();

        OrderStatus newOrderStatus;
        try {
            newOrderStatus = OrderStatus.valueOf(status);
        } catch (IllegalArgumentException e) {
            throw new InvalidOrderStatusException("Invalid order status: " + status);
        }

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new InvalidOrderStatusException(
                    "Order status cannot be updated from " + order.getStatus() + " to " + newOrderStatus);
        } else if (newOrderStatus == OrderStatus.SUCCESS) {
            RevenueResponse revenueResponse = new RevenueResponse();
            LocalDateTime createAt = order.getCreatedAt();
            revenueResponse.setDate(createAt.toLocalDate());
            revenueResponse.setTotalRevenue(order.getTotalPrice());
            revenueMapper.insert(revenueResponse);
        }

        orderMapper.updateOrderStatus(id, status);
        insertOrderHistory(id, status);
    }

    private void insertOrderHistory(Long orderId, String status) {
        OrderHistoryRequest orderHistoryRequest = OrderHistoryRequest.builder()
                .orderId(orderId)
                .status(status)
                .changedBy(customUserDetailsService.getCurrentUserId())
                .build();
        orderMapper.insertOrderHistory(orderHistoryRequest);
    }

    @Transactional
    public void cancelOrder(Long id) {
        OrderResponse order = getOrderById(id);

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new InvalidOrderStatusException(
                    "Order status cannot be cancelled, current status is: " + order.getStatus());
        }

        orderMapper.updateOrderStatus(id, OrderStatus.CANCEL.name());
        insertOrderHistory(id, OrderStatus.CANCEL.name());

        List<OrderItemResponse> orderItems = orderMapper.findOrderItems(id);
        for (OrderItemResponse orderItem : orderItems) {
            productMapper.decreaseSoldQuantity(orderItem.getProductId(), orderItem.getQuantity());
            productMapper.updateTotalQuantity(
                    orderItem.getProductId(),
                    productMapper.findById(orderItem.getProductId()).getTotalQuantity() + orderItem.getQuantity());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<OrderResponse> getAllOrders() {
        return orderMapper.findAll();
    }

    public OrderItemResponse getOrderItemById(Long id) {
        OrderItemResponse orderItem = orderMapper.findOrderItem(id);
        if (orderItem == null) {
            throw new DataNotFoundException(String.format("Order item with ID %d not found", id));
        }
        return orderItem;
    }

    public List<OrderHistoryResponse> getOrderHistories(Long orderId) {
        return orderMapper.findOrderHistories(orderId);
    }
}
