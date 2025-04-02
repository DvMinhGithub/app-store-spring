package com.mdv.appstore.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.mdv.appstore.dto.request.OrderCreateRequest;
import com.mdv.appstore.dto.request.OrderStatusRequest;
import com.mdv.appstore.dto.response.ApiResponse;
import com.mdv.appstore.dto.response.OrderHistoryResponse;
import com.mdv.appstore.dto.response.OrderItemResponse;
import com.mdv.appstore.dto.response.OrderResponse;
import com.mdv.appstore.service.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("${app.api.base-url}/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public ApiResponse<OrderResponse> getOrderById(@PathVariable("id") Long id) {
        return ApiResponse.success(orderService.getOrderById(id), "Order found");
    }

    @GetMapping("/user/{userId}")
    public ApiResponse<List<OrderResponse>> getOrdersByUserId(@PathVariable("userId") Long userId) {
        return ApiResponse.success(orderService.getOrdersByUserId(userId), "Orders found");
    }

    @GetMapping
    public ApiResponse<List<OrderResponse>> getAllOrders() {
        return ApiResponse.success(orderService.getAllOrders(), "Orders found");
    }

    @GetMapping("/item/{id}")
    public ApiResponse<OrderItemResponse> getOrderItemById(@PathVariable("id") Long id) {
        return ApiResponse.success(orderService.getOrderItemById(id), "Order item found");
    }

    @GetMapping("/{id}/history")
    public ApiResponse<List<OrderHistoryResponse>> getOrderHistories(@PathVariable("id") Long id) {
        return ApiResponse.success(orderService.getOrderHistories(id), "Order histories found");
    }

    @PostMapping
    public ApiResponse<Void> createOrder(@RequestBody @Valid OrderCreateRequest orderCreateRequest) {
        orderService.createOrder(orderCreateRequest);
        return ApiResponse.success("Order created");
    }

    @PutMapping("/{id}/status")
    public ApiResponse<Void> updateOrderStatus(
            @PathVariable("id") Long id, @RequestBody @Valid OrderStatusRequest orderStatusRequest) {
        orderService.updateOrderStatus(id, orderStatusRequest);
        return ApiResponse.success("Order status updated");
    }

    @DeleteMapping("/{id}/cancel")
    public ApiResponse<Void> cancelOrder(@PathVariable("id") Long id) {
        orderService.cancelOrder(id);
        return ApiResponse.success("Order canceled");
    }
}
