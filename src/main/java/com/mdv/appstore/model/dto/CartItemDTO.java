package com.mdv.appstore.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemDTO {
    private Long id;
    private Integer quantity;
    private ProductDTO product;
    private Long userId;
}
