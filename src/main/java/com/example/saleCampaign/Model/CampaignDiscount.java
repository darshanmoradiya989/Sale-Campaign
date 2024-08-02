package com.example.saleCampaign.Model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "campaign_discounts")
public class CampaignDiscount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "campaign_id")
    private Campaign campaign;

    @Column(name = "product_id")
    private int productId;

    @Column(name = "discount")
    private float discount;

    @Override
    public String toString() {
        return "CampaignDiscount{" +
                "campaignId=" + (campaign != null ? campaign.getId() : "N/A") +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

}
