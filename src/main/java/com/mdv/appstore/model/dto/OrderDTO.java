package com.mdv.appstore.model.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import com.mdv.appstore.enums.OrderStatus;

@Getter
@Setter
public class OrderDTO {
    private Long id;
    private String orderCode;
    private String address;
    private String phone;
    private OrderStatus status;
    private Double totalPrice;
    private String voucherCode;
    private Long userId;
    LocalDateTime createdAt;

    private List<OrderItemDTO> orderItems;
    private List<OrderHistoryDTO> orderHistories;
}
