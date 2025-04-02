package com.mdv.appstore.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.mdv.appstore.service.RevenueService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class RevenueScheduler {
    private final RevenueService revenueService;

    @Scheduled(cron = "0 0 0 * * *")
    public void scheduleDailyRevenueCalculation() {
        log.info("Started calculating daily revenue via cron job at midnight");
        try {
            revenueService.updateDailyRevenue();
            log.info("Completed calculating daily revenue via cron job");
        } catch (Exception e) {
            log.error("Failed to calculate daily revenue via cron job", e);
        }
    }
}
