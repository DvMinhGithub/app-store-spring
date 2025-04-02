package com.mdv.appstore.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponse {
    private Long id;
    private Long orderId;
    private Long productId;
    private Integer quantity;
    private Double priceAtOrderTime;
}
