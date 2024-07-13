package com.location1.TVSME_location.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "dealerCollection")
public class DealerInfo {

    @Id
    private String id;
    @Indexed(unique = true)
    private String sapDealerCode;
    private String updatedAddress;
    private UserCurrentLocation userCurrentLocation;
    private String photo;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserCurrentLocation {
        private String latitude;
        private String longitude;
    }
}
