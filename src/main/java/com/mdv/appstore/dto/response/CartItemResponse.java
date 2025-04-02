package com.mdv.appstore.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemResponse {
    private Long id;
    private Integer quantity;
    private ProductResponse product;
    private Long userId;
}
