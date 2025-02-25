package com.mdv.appstore.model.request;

// Import Jakarta validation constraints

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderHistoryRequest {

    @NotNull(message = "Order ID cannot be empty")
    @Positive(message = "Order ID must be a positive number")
    private Long orderId;

    @NotBlank(message = "Status cannot be blank")
    @Pattern(
            regexp = "^(CANCEL|SUCCESS|PENDING|RETURN)$",
            message = "Invalid Status. Allowed values are: CANCEL, SUCCESS, PENDING, RETURN")
    private String status;

    @NotNull(message = "Changed By User ID cannot be empty")
    @Positive(message = "Changed By User ID must be a positive number")
    private Long changedBy;
}
