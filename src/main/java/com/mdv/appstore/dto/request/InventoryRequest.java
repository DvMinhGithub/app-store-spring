package com.mdv.appstore.dto.request;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class InventoryRequest {
    @NotNull(message = "Product ID cannot be null")
    @Positive(message = "Product ID must be a positive number")
    private Long productId;

    @NotNull(message = "Import price cannot be null")
    @DecimalMin(value = "0.0", inclusive = true, message = "Import price must be greater than or equal to 0")
    private Double importPrice;

    @NotNull(message = "Quantity cannot be null")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    @NotNull(message = "Supplier ID cannot be null")
    @Positive(message = "Supplier ID must be a positive number")
    private Long supplierId;

    @NotBlank(message = "Batch code cannot be blank")
    @Length(max = 255, message = "Batch code must not exceed 255 characters")
    private String batchCode;
}
