package com.example.saleCampaign.Controller;

import com.example.saleCampaign.Model.Product;
import com.example.saleCampaign.Model.ResponseDTO;
import com.example.saleCampaign.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("saveProduct")
    public ResponseDTO<Product> saveProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    @GetMapping("getAllProducts")
    public ResponseDTO<List<Product>> saveProduct() {
        return productService.getProductList();
    }

    @GetMapping("getAllPaginated")
    public ResponseDTO<Page<Product>> getAllProductsPaginated(@RequestParam(defaultValue = "1") int page,
                                                                 @RequestParam(defaultValue = "10") int size) {
        return productService.getAllPaginated(page, size);
    }

    @PutMapping("updatePrice")
    public ResponseDTO<Product> updateProductPrice(@RequestHeader("productId") int productId, @RequestHeader("price") long price) {
        return productService.updateProductPrice(productId, price);
    }


}
