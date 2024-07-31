package com.example.saleCampaign.Repository;

import com.example.saleCampaign.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
