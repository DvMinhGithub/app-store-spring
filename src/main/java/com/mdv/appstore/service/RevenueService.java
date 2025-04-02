package com.mdv.appstore.service;

import java.util.List;

import com.mdv.appstore.dto.response.RevenueResponse;

public interface RevenueService {

    List<RevenueResponse> getRevenueByDate(String startDate, String endDate);

    Double getTotalRevenue(String startDate, String endDate);

    void updateDailyRevenue();
}
