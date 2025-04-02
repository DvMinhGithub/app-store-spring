package com.mdv.appstore.service;

import java.util.List;

import com.mdv.appstore.dto.request.OrderCreateRequest;
import com.mdv.appstore.dto.request.OrderStatusRequest;
import com.mdv.appstore.dto.response.OrderHistoryResponse;
import com.mdv.appstore.dto.response.OrderItemResponse;
import com.mdv.appstore.dto.response.OrderResponse;

public interface OrderService {

    OrderResponse getOrderById(Long id);

    void createOrder(OrderCreateRequest orderCreateRequest);

    List<OrderResponse> getOrdersByUserId(Long userId);

    void updateOrderStatus(Long id, OrderStatusRequest orderStatusRequest);

    void cancelOrder(Long id);

    List<OrderResponse> getAllOrders();

    OrderItemResponse getOrderItemById(Long id);

    List<OrderHistoryResponse> getOrderHistories(Long orderId);
}
