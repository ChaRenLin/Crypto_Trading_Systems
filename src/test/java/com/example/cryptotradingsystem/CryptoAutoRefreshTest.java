package com.example.cryptotradingsystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

import com.example.cryptotradingsystem.service.CryptoPriceService;

public class CryptoAutoRefreshTest {
	
	
	@InjectMocks
    private CryptoPriceService cryptoPriceService;

	@Mock
    private Constants constants;
	

	 @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFetchestPriceAndSizeThrowsException() {
        assertThrows(RuntimeException.class, () -> {
            System.out.println(cryptoPriceService.fetchestPriceAndSize(constants.CONSTANTS_BINANCE_ETHUSDT_URL, constants.CONSTANTS_ETHUSDT_L_NAME));
        });
    }

    @Test
    public void testFetchestPriceAndSizeHandlesException() {
        try {
        	System.out.println(cryptoPriceService.fetchestPriceAndSize(constants.CONSTANTS_BINANCE_ETHUSDT_URL, constants.CONSTANTS_ETHUSDT_L_NAME));
        } catch (RuntimeException e) {
            // Verify that the exception message is correct
            assertEquals("API failure", e.getMessage());
        }
    }
}
