package com.example.saleCampaign.Controller;

import com.example.saleCampaign.Model.Campaign;
import com.example.saleCampaign.Model.ResponseDTO;
import com.example.saleCampaign.Service.CampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("campaigns")
public class CampaignController {

    @Autowired
    CampaignService campaignService;

    @PostMapping("saveCampaign")
    public ResponseDTO<Campaign> saveCampaign(@RequestBody Campaign campaign) {
        return campaignService.saveCampaign(campaign);
    }
}
