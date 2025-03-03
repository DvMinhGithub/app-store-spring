package com.mdv.appstore.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.mdv.appstore.config.CustomUserDetailsService;
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
import com.mdv.appstore.model.dto.CartItemDTO;
import com.mdv.appstore.model.dto.OrderDTO;
import com.mdv.appstore.model.dto.OrderHistoryDTO;
import com.mdv.appstore.model.dto.OrderItemDTO;
import com.mdv.appstore.model.dto.RevenueDTO;
import com.mdv.appstore.model.dto.VoucherDTO;
import com.mdv.appstore.model.request.OrderCreateRequest;
import com.mdv.appstore.model.request.OrderHistoryRequest;
import com.mdv.appstore.model.request.OrderItemRequest;
import com.mdv.appstore.model.request.OrderStatusRequest;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderMapper orderMapper;
    private final UserMapper userMapper;
    private final CartItemMapper cartItemMapper;
    private final ProductMapper productMapper;
    private final VoucherMapper voucherMapper;
    private final RevenueMapper revenueMapper;
    private final CustomUserDetailsService customUserDetailsService;

    public OrderDTO getOrderById(Long id) {
        OrderDTO order = orderMapper.findById(id);
        if (order == null) {
            throw new DataNotFoundException(String.format("Order with ID %d not found", id));
        }
        return order;
    }

    @Transactional
    public void createOrder(OrderCreateRequest orderCreateRequest) throws RuntimeException {
        Long userId = customUserDetailsService.getCurrentUserId();
        orderCreateRequest.setUserId(userId);
        orderCreateRequest.setOrderCode(generateOrderCode());

        List<CartItemDTO> cartItems =
                cartItemMapper.findAllByIdsAndUserId(orderCreateRequest.getCartItemIds(), userId);
        if (cartItems.isEmpty()) {
            throw new CartEmptyException("Cart is empty");
        }

        double totalPriceOrder = 0;
        for (CartItemDTO cartItem : cartItems) {
            totalPriceOrder += cartItem.getProduct().getPrice() * cartItem.getQuantity();
        }

        VoucherDTO voucher = voucherMapper.selectVoucherByCode(orderCreateRequest.getVoucherCode());

        if (voucher != null) {
            Boolean isVoucherActive = voucher.getIsActive();
            if (Boolean.FALSE.equals(isVoucherActive)) {
                throw new VoucherNotActiveException("Voucher is not active");
            }

            if (voucher.getConditionValue() > totalPriceOrder) {
                throw new TotalPriceLessThanConditionException(
                        "Total price is less than minimum total price");
            }

            if (voucher.getDiscountPrice() > totalPriceOrder) {
                throw new DiscountGreaterThanTotalPriceException(
                        "Discount is greater than total price");
            }

            voucherMapper.updateUsedQuantity(voucher.getId());
            totalPriceOrder -= voucher.getDiscountPrice();
        } else {
            throw new DataNotFoundException(
                    String.format(
                            "Voucher with code %s not found", orderCreateRequest.getVoucherCode()));
        }

        orderCreateRequest.setTotalPrice(totalPriceOrder);
        orderCreateRequest.setStatus(OrderStatus.PENDING.name());
        orderMapper.insertOrder(orderCreateRequest);

        saveOrderDetailsAndProcessCartItems(orderCreateRequest, cartItems);
    }

    private void saveOrderDetailsAndProcessCartItems(
            OrderCreateRequest orderCreateRequest, List<CartItemDTO> cartItems) {
        for (CartItemDTO cartItem : cartItems) {
            OrderItemRequest orderItemRequest =
                    OrderItemRequest.builder()
                            .orderId(orderCreateRequest.getId())
                            .productId(cartItem.getProduct().getId())
                            .quantity(cartItem.getQuantity())
                            .priceAtOrderTime(cartItem.getProduct().getPrice())
                            .build();
            orderMapper.insertOrderDetail(orderItemRequest);

            cartItemMapper.deleteByCartItemIdAndUserId(
                    cartItem.getId(), customUserDetailsService.getCurrentUserId());

            Long newTotalQuantity =
                    cartItem.getProduct().getTotalQuantity() - cartItem.getQuantity();
            productMapper.updateTotalQuantity(cartItem.getProduct().getId(), newTotalQuantity);
            productMapper.increaseSoldQuantity(
                    cartItem.getProduct().getId(), cartItem.getQuantity());
        }
    }

    private String generateOrderCode() {
        return "ORDER-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public List<OrderDTO> getOrdersByUserId(Long userId) {
        if (userMapper.findById(userId) == null) {
            throw new DataNotFoundException(String.format("User with ID %d not found", userId));
        }
        return orderMapper.findAllByUserId(userId);
    }

    @Transactional
    public void updateOrderStatus(Long id, OrderStatusRequest orderStatusRequest) {
        OrderDTO order = getOrderById(id);
        String status = orderStatusRequest.getStatus().toUpperCase();

        OrderStatus newOrderStatus;
        try {
            newOrderStatus = OrderStatus.valueOf(status);
        } catch (IllegalArgumentException e) {
            throw new InvalidOrderStatusException("Invalid order status: " + status);
        }

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new InvalidOrderStatusException(
                    "Order status cannot be updated from "
                            + order.getStatus()
                            + " to "
                            + newOrderStatus);
        } else if (newOrderStatus == OrderStatus.SUCCESS) {
            RevenueDTO revenueDTO = new RevenueDTO();
            LocalDateTime createAt = order.getCreatedAt();
            revenueDTO.setDate(createAt.toLocalDate());
            revenueDTO.setTotalRevenue(order.getTotalPrice());
            revenueMapper.insert(revenueDTO);
        }

        orderMapper.updateOrderStatus(id, status);
        insertOrderHistory(id, status);
    }

    private void insertOrderHistory(Long orderId, String status) {
        OrderHistoryRequest orderHistoryRequest =
                OrderHistoryRequest.builder()
                        .orderId(orderId)
                        .status(status)
                        .changedBy(customUserDetailsService.getCurrentUserId())
                        .build();
        orderMapper.insertOrderHistory(orderHistoryRequest);
    }

    @Transactional
    public void cancelOrder(Long id) {
        OrderDTO order = getOrderById(id);

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new InvalidOrderStatusException(
                    "Order status cannot be cancelled, current status is: " + order.getStatus());
        }

        orderMapper.updateOrderStatus(id, OrderStatus.CANCEL.name());
        insertOrderHistory(id, OrderStatus.CANCEL.name());

        List<OrderItemDTO> orderItems = orderMapper.findOrderItems(id);
        for (OrderItemDTO orderItem : orderItems) {
            productMapper.decreaseSoldQuantity(orderItem.getProductId(), orderItem.getQuantity());
            productMapper.updateTotalQuantity(
                    orderItem.getProductId(),
                    productMapper.findById(orderItem.getProductId()).getTotalQuantity()
                            + orderItem.getQuantity());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<OrderDTO> getAllOrders() {
        return orderMapper.findAll();
    }

    public OrderItemDTO getOrderItemById(Long id) {
        OrderItemDTO orderItem = orderMapper.findOrderItem(id);
        if (orderItem == null) {
            throw new DataNotFoundException(String.format("Order item with ID %d not found", id));
        }
        return orderItem;
    }

    public List<OrderHistoryDTO> getOrderHistories(Long orderId) {
        return orderMapper.findOrderHistories(orderId);
    }
}
