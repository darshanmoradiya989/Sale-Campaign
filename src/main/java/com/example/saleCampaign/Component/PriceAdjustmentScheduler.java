package com.example.saleCampaign.Component;

import com.example.saleCampaign.Model.Campaign;
import com.example.saleCampaign.Model.CampaignDiscount;
import com.example.saleCampaign.Model.Product;
import com.example.saleCampaign.Repository.CampaignRepository;
import com.example.saleCampaign.Repository.ProductRepository;
import com.example.saleCampaign.Service.ProductService;
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

    @Autowired
    ProductService productService;

    @Scheduled(cron = "0 0 0 * * *")
    public void adjustProductPrices() {
        LocalDate today = LocalDate.now();
        List<Campaign> activeSales = campaignRepository.findAllByStartDate(today);
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

                    if (newPrice >= 0) {
                        product.setCurrentPrice(newPrice);
                        product.setDiscount(discount.getDiscount());
                        productRepository.save(product);
                        productService.saveHistory(product, newPrice, LocalDate.now(), product.getDiscount());
                    }
                }
            }
        }
    }


    @Scheduled(cron = "0 0 0 * * *")
    public void revertPrice(){
        LocalDate today = LocalDate.now();
        List<Campaign> endedSales = campaignRepository.findAllByEndDate(today);
        for (Campaign campaign : endedSales) {
            List<CampaignDiscount> discounts = campaign.getCampaignDiscounts();
            for (CampaignDiscount discount : discounts) {
                Product product = productRepository.findById(discount.getProductId()).orElse(null);
                if (product != null) {

                }
            }
        }
    }


}
