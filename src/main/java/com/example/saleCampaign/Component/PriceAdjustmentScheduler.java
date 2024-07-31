package com.example.saleCampaign.Component;

import com.example.saleCampaign.Model.Campaign;
import com.example.saleCampaign.Model.CampaignDiscount;
import com.example.saleCampaign.Model.Product;
import com.example.saleCampaign.Repository.CampaignRepository;
import com.example.saleCampaign.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.List;


@Component
public class PriceAdjustmentScheduler {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CampaignRepository campaignRepository;

    @Scheduled(cron = "0 0 0 * * *")
    public void adjustProductPrices() {
        LocalDate today = LocalDate.now();
        List<Campaign> activeSales = campaignRepository.findByStartDateBeforeAndEndDateAfter(today, today);
        System.out.println(activeSales);
        for (Campaign campaign : activeSales) {
            List<CampaignDiscount> discounts = campaign.getCampaignDiscounts();
            System.out.println(campaign);
            for (CampaignDiscount discount : discounts) {
                Product product = productRepository.findById(discount.getProductId()).orElse(null);
                System.out.println(product);
                if (product != null) {
                    float discountAmount = product.getCurrentPrice() * (discount.getDiscount() / 100);
                    long newPrice = (long) (product.getCurrentPrice() - discountAmount);

                    // Ensure the new price is not negative
                    if (newPrice >= 0) {
                        product.setCurrentPrice(newPrice);
                        productRepository.save(product);
                    }
                }
            }
        }

    }

    // Revert prices for ended sales
    @Scheduled(cron = "0 0 0 * * *")
    public void revertPrice(){
        LocalDate today = LocalDate.now();
        List<Campaign> endedSales = campaignRepository.findByEndDateBefore(today);
        for (Campaign campaign : endedSales) {
            List<CampaignDiscount> discounts = campaign.getCampaignDiscounts();

            for (CampaignDiscount discount : discounts) {
                Product product = productRepository.findById(discount.getProductId()).orElse(null);
                if (product != null) {
                    long originalPrice = product.getMrp(); // Assuming MRP is the original price
                    product.setCurrentPrice(originalPrice);
                    productRepository.save(product);
                }
            }
        }
    }


}
