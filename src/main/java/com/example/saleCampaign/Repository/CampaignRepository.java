package com.example.saleCampaign.Repository;

import com.example.saleCampaign.Model.Campaign;
import com.example.saleCampaign.Model.CampaignDiscount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CampaignRepository extends JpaRepository<Campaign, Integer> {
        @Query(value = "SELECT * FROM campaigns WHERE start_date = :startDate", nativeQuery = true)
        List<Campaign> findAllByStartDate(@Param("startDate") LocalDate startDate);

        @Query(value="SELECT * FROM campaigns where end_date = :endDate",nativeQuery = true)
        List<Campaign> findAllByEndDate(@Param("endDate") LocalDate endDate);

}
