package com.location1.TVSME_location.service;

import com.location1.TVSME_location.dao.DealerInfoRepository;
import com.location1.TVSME_location.entity.DealerInfo;
import com.location1.TVSME_location.entity.DealerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DealerInfoService {

    @Autowired
    private DealerInfoRepository dealerInfoRepository;

    public DealerResponse saveDealerIfSapCodeExists(DealerInfo dealerInfo) {
        Optional<DealerInfo> existingDealerOpt = dealerInfoRepository.findBySapDealerCode(dealerInfo.getSapDealerCode());

        if (existingDealerOpt.isPresent()) {
            DealerInfo existingDealer = existingDealerOpt.get();
            existingDealer.setUpdatedAddress(dealerInfo.getUpdatedAddress());
            existingDealer.setUserCurrentLocation(dealerInfo.getUserCurrentLocation());
            existingDealer.setPhoto(dealerInfo.getPhoto());
            dealerInfoRepository.save(existingDealer);
            return new DealerResponse(true, "Dealer information  updated successfully.");
        } else {
            return new DealerResponse(false, "Dealer with the given sapDealerCode does not exist.");
        }
    }


}
