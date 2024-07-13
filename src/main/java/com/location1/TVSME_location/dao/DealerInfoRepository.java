package com.location1.TVSME_location.dao;

import com.location1.TVSME_location.entity.DealerInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface DealerInfoRepository extends MongoRepository<DealerInfo, String> {
    @Query("{ 'sapDealerCode': ?0 }")
    Optional<DealerInfo> findBySapDealerCode(String sapDealerCode);
}