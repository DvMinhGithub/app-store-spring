package com.mdv.appstore.dto.response;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RevenueResponse {
    private Long id;
    private LocalDate date;
    private Double totalRevenue;
}
