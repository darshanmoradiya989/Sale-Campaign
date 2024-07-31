package com.example.saleCampaign.Repository;

import com.example.saleCampaign.Model.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CampaignRepository extends JpaRepository<Campaign, Integer> {
    List<Campaign> findByStartDateBeforeAndEndDateAfter(LocalDate startDate, LocalDate endDate);
    List<Campaign> findByEndDateBefore(LocalDate endDate);
}
