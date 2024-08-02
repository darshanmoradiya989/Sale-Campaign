package com.example.saleCampaign.Component;

import com.example.saleCampaign.Model.Campaign;
import com.example.saleCampaign.Model.CampaignDiscount;
import com.example.saleCampaign.Model.PriceHistory;
import com.example.saleCampaign.Model.Product;
import com.example.saleCampaign.Repository.CampaignRepository;
import com.example.saleCampaign.Repository.PriceHistoryRepository;
import com.example.saleCampaign.Repository.ProductRepository;
import com.example.saleCampaign.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class PriceAdjustmentScheduler {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    PriceHistoryRepository priceHistoryRepository;

    @Scheduled(cron = "0 0 0 * * *")
    public void adjustProductPrices() {
        LocalDate today = LocalDate.now();
        List<Campaign> activeSales = campaignRepository.findAllByStartDate(today);

        for (Campaign campaign : activeSales) {
            List<CampaignDiscount> discounts = campaign.getCampaignDiscounts();
            for (CampaignDiscount discount : discounts) {
                Product product = productRepository.findById(discount.getProductId()).orElse(null);
                if (product != null) {
                    double discountAmount =  (product.getCurrentPrice() * (discount.getDiscount() / 100));
                    double newPrice = (product.getCurrentPrice() - discountAmount);

                    if (newPrice >= 0) {
                        product.setCurrentPrice(newPrice);
                        product.setDiscount(discount.getDiscount());
                        productRepository.save(product);
                        productService.saveHistory(product, newPrice, LocalDate.now(), discountAmount);
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

                    LocalDate date = campaign.getStartDate();
                    PriceHistory priceHistory = priceHistoryRepository.findTopByProductIdAndDate(product.getId(), date);
                    if (priceHistory != null) {
                        double previousPrice = priceHistory.getDiscountPrice();
                        product.setCurrentPrice(priceHistory.getPrice() + previousPrice);
                        productRepository.save(product);
                        productService.saveHistory(product, priceHistory.getPrice() + previousPrice, LocalDate.now(), priceHistory.getDiscountPrice());
                    }

                }
            }
        }
    }


}
