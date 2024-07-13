package com.location1.TVSME_location;

import com.location1.TVSME_location.dao.DealerInfoRepository;
import com.location1.TVSME_location.entity.DealerInfo;
import com.location1.TVSME_location.entity.DealerResponse;
import com.location1.TVSME_location.service.DealerInfoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LocalLocationTesting {

    @Autowired
    private DealerInfoRepository dealerInfoRepository;

    @Autowired
    private DealerInfoService dealerInfoService;

    @Test
    public void testFindBySapDealerCode() {

        String sapDealerCode = "D123409";  // Replace with a valid sapDealerCode in your database
        Optional<DealerInfo> dealerInfo = dealerInfoRepository.findBySapDealerCode(sapDealerCode);

        if (dealerInfo.isPresent()) {
            System.out.println("Found Dealer: " + dealerInfo.get());
        } else {
            System.out.println("Dealer not found!");
        }
    }

    @Test
    public void testSaveDealerIfSapCodeExists() {
        // Test with an existing sapDealerCode
        DealerInfo existingDealer = new DealerInfo();
        existingDealer.setSapDealerCode("D123406"); // Existing sapDealerCode
        existingDealer.setUpdatedAddress("New Delhi,Rk Puram,India");
        existingDealer.setUserCurrentLocation(new DealerInfo.UserCurrentLocation("12.9715987", "77.594566"));
        existingDealer.setPhoto("newTestPhoto");

        DealerResponse response = dealerInfoService.saveDealerIfSapCodeExists(existingDealer);
        System.out.println("sapDealerCode: " + existingDealer.getSapDealerCode() + " - " + response.getMessage());

        // Test with a non-existing sapDealerCode
        DealerInfo nonExistingDealer = new DealerInfo();
        nonExistingDealer.setSapDealerCode("D1234081"); // Non-existing sapDealerCode
        nonExistingDealer.setUpdatedAddress("Another Test St, Test City");
        nonExistingDealer.setUserCurrentLocation(new DealerInfo.UserCurrentLocation("12.9715987", "77.594566"));
        nonExistingDealer.setPhoto("anotherTestPhoto");

        response = dealerInfoService.saveDealerIfSapCodeExists(nonExistingDealer);
        System.out.println("sapDealerCode: " + nonExistingDealer.getSapDealerCode() + " - " + response.getMessage());
    }
}
