package com.mdv.appstore.schedule;

import com.mdv.appstore.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class VoucherScheduler {
    @Autowired private VoucherService voucherService;

    @Scheduled(cron = "0 0 0 * * *")
    public void scheduleVoucherActivationDeactivation() {
        System.out.println("Starting cron job to update voucher status...");
        voucherService.updateVoucherActiveStatus();
        System.out.println("Cron job to update voucher status completed.");
    }
}
