package com.mdv.appstore.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreateRequest {
    private Long id;

    private Long userId;

    private String voucherCode;

    private String orderCode;

    @Pattern(regexp = "CANCEL|SUCCESS|PENDING|RETURN")
    private String status;

    private Double totalPrice;

    @NotBlank(message = "Address cannot be blank")
    private String address;

    @NotBlank(message = "Phone cannot be blank")
    @Pattern(regexp = "^\\d{10}$", message = "Phone must be exactly 10 digits")
    private String phone;

    private String checkoutUrl;

    @NotNull(message = "Order lines cannot be null")
    private List<Long> cartItemIds;
}
