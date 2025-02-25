package com.mdv.appstore.model.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRequest {
    @NotNull(message = "Product ID cannot be empty")
    @Positive(message = "Product ID must be a positive number")
    private Long orderId;

    @NotNull(message = "Product ID cannot be empty")
    @Positive(message = "Product ID must be a positive number")
    private Long productId;

    @NotNull(message = "Quantity cannot be empty")
    @Positive(message = "Quantity must be positive")
    private Integer quantity;

    private Double priceAtOrderTime;
}
