package com.example.saleCampaign.Component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SchedulerTester implements CommandLineRunner{

    @Autowired
    private PriceAdjustmentScheduler priceAdjustmentScheduler;

    @Override
    public void run(String... args) throws Exception {
//        System.out.println("Manually running adjustProductPrices...");
//        priceAdjustmentScheduler.adjustProductPrices();
//
//        System.out.println("Manually running revertPrice...");
//        priceAdjustmentScheduler.revertPrice();
    }
}
