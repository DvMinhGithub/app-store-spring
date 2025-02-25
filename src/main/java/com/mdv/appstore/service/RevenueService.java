package com.mdv.appstore.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.mdv.appstore.mapper.OrderMapper;
import com.mdv.appstore.mapper.RevenueMapper;
import com.mdv.appstore.model.dto.RevenueDTO;

@Service
@RequiredArgsConstructor
@Slf4j
public class RevenueService {
    private final RevenueMapper revenueMapper;
    private final OrderMapper orderMapper;

    public List<RevenueDTO> getRevenueByDate(String startDate, String endDate) {
        return revenueMapper.findByDateRange(startDate, endDate);
    }

    public Double getTotalRevenue(String startDate, String endDate) {
        Double totalRevenue = revenueMapper.getTotalRevenue(startDate, endDate);
        return totalRevenue != null ? totalRevenue : 0;
    }

    public void updateDailyRevenue() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        String dateStr = yesterday.format(DateTimeFormatter.ISO_LOCAL_DATE);

        log.info("Calculating revenue for date: {}", dateStr);

        Double revenue = orderMapper.getTotalRevenue(dateStr + " 00:00:00", dateStr + " 23:59:59");
        if (revenue != null && revenue > 0) {
            RevenueDTO revenueDTO = new RevenueDTO();
            revenueDTO.setDate(yesterday);
            revenueDTO.setTotalRevenue(revenue);

            revenueMapper.insert(revenueDTO);
            log.info("Saved revenue for date: {}, amount: {}", dateStr, revenue);
        } else {
            log.info("No revenue to save for date: {}", dateStr);
        }
    }
}
