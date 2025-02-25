package com.mdv.appstore.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.mdv.appstore.model.dto.RevenueDTO;
import com.mdv.appstore.model.response.ApiResponse;
import com.mdv.appstore.service.RevenueService;

@RestController
@RequestMapping("${app.api.base-url}/revenue")
@RequiredArgsConstructor
public class RevenueController {
    private final RevenueService revenueService;

    @GetMapping("/total")
    public ApiResponse<Double> getTotalRevenue(
            @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) {
        return ApiResponse.success(
                revenueService.getTotalRevenue(startDate, endDate), "Get total revenue successful");
    }

    @GetMapping("/list")
    public ApiResponse<List<RevenueDTO>> getRevenueByDate(
            @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) {
        return ApiResponse.success(
                revenueService.getRevenueByDate(startDate, endDate), "Get revenue list successful");
    }
}
