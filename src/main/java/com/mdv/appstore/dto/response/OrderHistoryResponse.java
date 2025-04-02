package com.mdv.appstore.dto.response;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderHistoryResponse {
    private Long id;
    private Long orderId;
    private UserResponse changedBy;
    private String status;
    private LocalDateTime changedAt;
}
