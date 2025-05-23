package com.mdv.appstore.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class VoucherCreateRequest {
    Long id;

    @NotBlank(message = "Code is required")
    String code;

    @NotBlank(message = "Discount type is required")
    @Pattern(regexp = "PERCENTAGE|FIXED_AMOUNT", message = "Discount type must be PERCENTAGE or FIXED_AMOUNT")
    String discountType;

    @NotNull(message = "Discount value is required")
    @Min(value = 0, message = "Discount value must be greater than 0")
    Double discountValue;

    @NotNull(message = "Min order amount is required")
    @Min(value = 0, message = "Min order amount must be greater than 0")
    Double minOrderAmount;

    @NotNull(message = "End date is required")
    @Future(message = "End date must be in the future")
    LocalDate endDate;

    @NotNull(message = "Start date is required")
    @FutureOrPresent(message = "Start date must be in the future")
    LocalDate startDate;

    @NotNull(message = "Max uses is required")
    @Min(value = 0, message = "Max uses must be greater than 0")
    Integer maxUses;
}
