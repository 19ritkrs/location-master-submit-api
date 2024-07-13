package com.location1.TVSME_location.service;

import com.location1.TVSME_location.dao.DealerInfoRepository;
import com.location1.TVSME_location.entity.DealerInfo;
import com.location1.TVSME_location.entity.DealerResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DealerInfoServiceTest {

    @Mock
    private DealerInfoRepository dealerInfoRepository;

    @InjectMocks
    private DealerInfoService dealerInfoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveDealerIfSapCodeExists(){
        DealerInfo existingDealer=new DealerInfo();
        existingDealer.setSapDealerCode("D123405");
        existingDealer.setUpdatedAddress("New Delhi,Rk Puram,India");
        existingDealer.setUserCurrentLocation(new DealerInfo.UserCurrentLocation("12.9715987", "77.594566"));
        existingDealer.setPhoto("newTestPhoto");

        when(dealerInfoRepository.findBySapDealerCode("D123405")).thenReturn(Optional.of(existingDealer));

        DealerInfo newDealerInfo=new DealerInfo();
        newDealerInfo.setSapDealerCode("D123405");
        newDealerInfo.setUpdatedAddress("New Test St, Test City");
        newDealerInfo.setUserCurrentLocation(new DealerInfo.UserCurrentLocation("12.9715987", "77.594566"));
        newDealerInfo.setPhoto("newTestPhoto");

        DealerResponse response = dealerInfoService.saveDealerIfSapCodeExists(newDealerInfo);


        assertTrue(response.isSuccess());
        assertEquals("Dealer information  updated successfully.", response.getMessage());

        verify(dealerInfoRepository, times(1)).save(existingDealer);
        assertEquals("New Test St, Test City", existingDealer.getUpdatedAddress());
        assertEquals("12.9715987", existingDealer.getUserCurrentLocation().getLatitude());
        assertEquals("77.594566", existingDealer.getUserCurrentLocation().getLongitude());
        assertEquals("newTestPhoto", existingDealer.getPhoto());

    }
    @Test
    public void testSaveDealerIfSapCodeDoesNotExist() {
        // Arrange
        DealerInfo newDealerInfo = new DealerInfo();
        newDealerInfo.setSapDealerCode("D999999"); // Non-existent sapDealerCode
        newDealerInfo.setUpdatedAddress("Non-existent Address");
        newDealerInfo.setUserCurrentLocation(new DealerInfo.UserCurrentLocation("0.0", "0.0"));
        newDealerInfo.setPhoto("nonExistentPhoto");

        when(dealerInfoRepository.findBySapDealerCode("D999999")).thenReturn(Optional.empty());

        // Act
        DealerResponse response = dealerInfoService.saveDealerIfSapCodeExists(newDealerInfo);

        // Assert
        assertFalse(response.isSuccess());
        assertEquals("Dealer with the given sapDealerCode does not exist.", response.getMessage());
    }

    @Test
    public void testSaveDealerWithNullFields() {
        // Arrange
        DealerInfo dealerInfoWithNullFields = new DealerInfo();
        dealerInfoWithNullFields.setSapDealerCode("D123410"); // Non-existent sapDealerCode
        dealerInfoWithNullFields.setUpdatedAddress(null); // Null address
        dealerInfoWithNullFields.setUserCurrentLocation(null); // Null location
        dealerInfoWithNullFields.setPhoto(null); // Null photo

        when(dealerInfoRepository.findBySapDealerCode("D123410")).thenReturn(Optional.empty());

        // Act
        DealerResponse response = dealerInfoService.saveDealerIfSapCodeExists(dealerInfoWithNullFields);

        // Assert
        assertFalse(response.isSuccess());
        assertEquals("Dealer with the given sapDealerCode does not exist.", response.getMessage());
    }

    @Test
    public void testSaveDealerWithEmptyFields() {
        // Arrange
        DealerInfo dealerInfoWithEmptyFields = new DealerInfo();
        dealerInfoWithEmptyFields.setSapDealerCode("D123411"); // Non-existent sapDealerCode
        dealerInfoWithEmptyFields.setUpdatedAddress(""); // Empty address
        dealerInfoWithEmptyFields.setUserCurrentLocation(new DealerInfo.UserCurrentLocation("", "")); // Empty location
        dealerInfoWithEmptyFields.setPhoto(""); // Empty photo

        when(dealerInfoRepository.findBySapDealerCode("D123411")).thenReturn(Optional.empty());

        // Act
        DealerResponse response = dealerInfoService.saveDealerIfSapCodeExists(dealerInfoWithEmptyFields);

        // Assert
        assertFalse(response.isSuccess());
        assertEquals("Dealer with the given sapDealerCode does not exist.", response.getMessage());
    }



}
