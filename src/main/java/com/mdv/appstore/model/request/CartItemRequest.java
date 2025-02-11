package com.mdv.appstore.model.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemRequest {
    @NotNull(message = "Quantity cannot be empty")
    @Min(value = 1, message = "Quantity must be greater than or equal to 1")
    private Integer quantity;

    @NotNull(message = "Product ID cannot be empty")
    @Positive(message = "Product ID must be a positive number")
    private Long productId;

    @Positive(message = "User ID must be a positive number")
    private Long userId;
}
