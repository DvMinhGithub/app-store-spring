package com.mdv.appstore.model.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusRequest {

    @NotNull(message = "Status cannot be empty")
    @Pattern(regexp = "CANCEL|SUCCESS|PENDING|RETURN")
    private String status;
}
