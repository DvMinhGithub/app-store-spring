package com.mdv.appstore.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VoucherUpdateRequest {
    @NotBlank(message = "Code is required")
    String code;

    @Pattern(regexp = "PERCENTAGE|FIXED_AMOUNT", message = "Discount type must be PERCENTAGE or FIXED_AMOUNT")
    String discountType;

    @Min(value = 0, message = "Discount value must be greater than 0")
    Double discountValue;

    @Min(value = 0, message = "Min order amount must be greater than 0")
    Double minOrderAmount;

    @Future(message = "End date must be in the future")
    LocalDate endDate;

    @FutureOrPresent(message = "Start date must be in the future")
    LocalDate startDate;

    @Min(value = 0, message = "Max uses must be greater than 0")
    Integer maxUses;

    Integer currentUses;
    Boolean isActive;
}
