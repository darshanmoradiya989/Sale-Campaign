package com.example.saleCampaign.Service;

import com.example.saleCampaign.Model.Campaign;
import com.example.saleCampaign.Model.Product;
import com.example.saleCampaign.Model.ResponseDTO;
import com.example.saleCampaign.Repository.CampaignRepository;
import com.example.saleCampaign.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public ResponseDTO<Product> saveProduct(Product product){
        try{
            return new ResponseDTO<>(productRepository.save(product), HttpStatus.OK, "Product saved successfully");
        } catch (Exception e){
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

    public ResponseDTO<Page<Product>> getAllHousesPaginated(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page - 1, size);
            Page<Product> housesPage = productRepository.findAll(pageable);
            return new ResponseDTO<>(housesPage, HttpStatus.OK, "get houses");
        } catch (Exception e) {
            return new ResponseDTO<>(null, HttpStatus.INTERNAL_SERVER_ERROR, "failed to get houses " + e.getMessage());
        }
    }



}
