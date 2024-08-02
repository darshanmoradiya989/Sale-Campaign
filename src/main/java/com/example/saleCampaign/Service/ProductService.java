package com.example.saleCampaign.Service;

import com.example.saleCampaign.Model.*;
import com.example.saleCampaign.Repository.CampaignRepository;
import com.example.saleCampaign.Repository.PriceHistoryRepository;
import com.example.saleCampaign.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private PriceHistoryRepository priceHistoryRepository;

    public ResponseDTO<Product> saveProduct(Product product){
        try{
            Product savedProduct = productRepository.save(product);
            double discountAmount =  (product.getCurrentPrice() * (product.getDiscount() / 100));
            saveHistory(product, product.getCurrentPrice(), LocalDate.now(), discountAmount);
            return new ResponseDTO<>(savedProduct, HttpStatus.OK, "Product saved successfully");
        } catch (Exception e){
            return new ResponseDTO<>(null, HttpStatus.INTERNAL_SERVER_ERROR, "failed to save product" + e.getMessage());
        }
    }

    public  ResponseDTO<List<Product>> saveAllProduct(List<Product> product) {
        try {
            List<Product> productsList = productRepository.saveAll(product);
            for (Product products : productsList){
                double discountAmount =  (products.getCurrentPrice() * (products.getDiscount() / 100));
                saveHistory(products, products.getCurrentPrice(), LocalDate.now(), discountAmount);
            }
            return new ResponseDTO<>(productsList, HttpStatus.OK, "Successfully saved");
        } catch (Exception e) {
            return new ResponseDTO<>(null, HttpStatus.INTERNAL_SERVER_ERROR, "failed to save product" + e.getMessage());
        }
    }

    public ResponseDTO<List<Product>> getProductList(){
        try {
            return new ResponseDTO<>(productRepository.findAll(), HttpStatus.OK, "product list");
        } catch (Exception e){
            return new ResponseDTO<>(null, HttpStatus.INTERNAL_SERVER_ERROR, "failed to get " + e.getMessage());
        }
    }

    public ResponseDTO<Page<Product>> getAllPaginated(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page - 1, size);
            Page<Product> housesPage = productRepository.findAll(pageable);
            return new ResponseDTO<>(housesPage, HttpStatus.OK, "get houses");
        } catch (Exception e) {
            return new ResponseDTO<>(null, HttpStatus.INTERNAL_SERVER_ERROR, "failed to get houses " + e.getMessage());
        }
    }

    public void saveHistory(Product product, double price, LocalDate date, double discountPrice){
        PriceHistory priceHistory = new PriceHistory();
        priceHistory.setProduct(product);
        priceHistory.setPrice(price);
        priceHistory.setLocalDate(date);
        priceHistory.setDiscountPrice(discountPrice);
        priceHistoryRepository.save(priceHistory);
    }

    public ResponseDTO<Product> updateProductPrice(int productId, double price) {
        try {
            Product product = productRepository.findById(productId).orElse(null);
            if (product == null) {
                return new ResponseDTO<>(null, HttpStatus.NOT_FOUND, "Product not found");
            }
            if (product.getCurrentPrice()!= price) {
                product.setCurrentPrice(price);
                productRepository.save(product);
                double discountAmount =  (product.getCurrentPrice() * (product.getDiscount() / 100));
                saveHistory(product, price, LocalDate.now(), discountAmount);
            }
            return new ResponseDTO<>(product, HttpStatus.OK, "Product price updated successfully");
        } catch (Exception e) {
            return new ResponseDTO<>(null, HttpStatus.INTERNAL_SERVER_ERROR, "failed to update product price " + e.getMessage());
        }
    }

}
