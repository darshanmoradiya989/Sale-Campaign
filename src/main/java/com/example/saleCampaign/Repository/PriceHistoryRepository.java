package com.example.saleCampaign.Repository;

import com.example.saleCampaign.Model.PriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceHistoryRepository extends JpaRepository<PriceHistory, Integer> {
}
