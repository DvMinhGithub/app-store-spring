package com.mdv.appstore.model.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderHistoryDTO {
    private Long id;
    private Long orderId;
    private UserDTO changedBy;
    private String status;
    private LocalDateTime changedAt;
}
