package com.mdv.appstore.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.mdv.appstore.enums.OrderStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderResponse {
    private Long id;
    private String orderCode;
    private String address;
    private String phone;
    private OrderStatus status;
    private Double totalPrice;
    private String voucherCode;
    private Long userId;
    LocalDateTime createdAt;

    private List<OrderItemResponse> orderItems;
    private List<OrderHistoryResponse> orderHistories;
}
