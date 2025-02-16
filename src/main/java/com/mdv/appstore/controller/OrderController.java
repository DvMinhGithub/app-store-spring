package com.mdv.appstore.controller;

import com.mdv.appstore.model.dto.OrderDTO;
import com.mdv.appstore.model.dto.OrderHistoryDTO;
import com.mdv.appstore.model.dto.OrderItemDTO;
import com.mdv.appstore.model.request.OrderCreateRequest;
import com.mdv.appstore.model.request.OrderStatusRequest;
import com.mdv.appstore.model.response.ApiResponse;
import com.mdv.appstore.service.OrderService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${app.api.base-url}/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public ApiResponse<OrderDTO> getOrderById(@PathVariable("id") Long id) {
        return ApiResponse.success(orderService.getOrderById(id), "Order found");
    }

    @GetMapping("/user/{userId}")
    public ApiResponse<List<OrderDTO>> getOrdersByUserId(@PathVariable("userId") Long userId) {
        return ApiResponse.success(orderService.getOrdersByUserId(userId), "Orders found");
    }

    @GetMapping
    public ApiResponse<List<OrderDTO>> getAllOrders() {
        return ApiResponse.success(orderService.getAllOrders(), "Orders found");
    }

    @GetMapping("/item/{id}")
    public ApiResponse<OrderItemDTO> getOrderItemById(@PathVariable("id") Long id) {
        return ApiResponse.success(orderService.getOrderItemById(id), "Order item found");
    }

    @GetMapping("/{id}/history")
    public ApiResponse<List<OrderHistoryDTO>> getOrderHistories(@PathVariable("id") Long id) {
        return ApiResponse.success(orderService.getOrderHistories(id), "Order histories found");
    }

    @PostMapping
    public ApiResponse<Void> createOrder(
            @RequestBody @Valid OrderCreateRequest orderCreateRequest) {
        orderService.createOrder(orderCreateRequest);
        return ApiResponse.success("Order created");
    }

    @PutMapping("/{id}/status")
    public ApiResponse<Void> updateOrderStatus(
            @PathVariable("id") Long id,
            @RequestBody @Valid OrderStatusRequest orderStatusRequest) {
        orderService.updateOrderStatus(id, orderStatusRequest);
        return ApiResponse.success("Order status updated");
    }

    @DeleteMapping("/{id}/cancel")
    public ApiResponse<Void> cancelOrder(@PathVariable("id") Long id) {
        orderService.cancelOrder(id);
        return ApiResponse.success("Order canceled");
    }
}
