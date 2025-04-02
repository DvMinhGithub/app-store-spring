package com.mdv.appstore.dto.request;

import java.util.Date;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ProductPromotionRequest {
    @NotNull(message = "Product ID cannot be null")
    @Positive(message = "Product ID must be a positive number")
    private Long productId;

    @NotNull(message = "Start time cannot be null")
    @FutureOrPresent(message = "Start time must be in the present or future")
    private Date startTime;

    @NotNull(message = "End time cannot be null")
    @Future(message = "End time must be in the future")
    private Date endTime;

    @NotNull(message = "Discount price cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Discount price must be greater than 0")
    private Double discountPrice;

    private Boolean isActive;
}
