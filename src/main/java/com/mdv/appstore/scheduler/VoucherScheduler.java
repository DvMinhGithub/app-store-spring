package com.mdv.appstore.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

import com.mdv.appstore.service.VoucherService;

@Component
@Slf4j
public class VoucherScheduler {

    private VoucherService voucherService;

    public VoucherScheduler(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @PostConstruct
    public void runOnStartup() {
        log.info("Started updating voucher status on server startup");
        try {
            int updatedCount = voucherService.updateVoucherActiveStatus();
            log.info(
                    "Completed updating voucher status on server startup, updated {} vouchers",
                    updatedCount);
        } catch (Exception e) {
            log.error("Failed to update voucher status on server startup", e);
        }
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void scheduleVoucherActivationDeactivation() {
        log.info("Started updating voucher status via cron job at midnight");
        try {
            int updatedCount = voucherService.updateVoucherActiveStatus();
            log.info(
                    "Completed updating voucher status via cron job, updated {} vouchers",
                    updatedCount);
        } catch (Exception e) {
            log.error("Failed to update voucher status via cron job", e);
        }
    }
}
