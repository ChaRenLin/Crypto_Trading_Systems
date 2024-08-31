package com.example.cryptotradingsystem.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.cryptotradingsystem.Constants;

@Component
public class CryptoPriceScheduler {

    @Autowired
    private CryptoPriceService cryptoPriceService;

    @Autowired
    private Constants constants;
    
    @Scheduled(fixedRate = 20000) // Run every 10 seconds
    public void fetchPrices() {
    	Map<String, Object> aggregatedEthustPrices = cryptoPriceService.fetchestPriceAndSize(
    			constants.CONSTANTS_BINANCE_ETHUSDT_URL, constants.CONSTANTS_ETHUSDT_L_NAME);
        
    	
    	Map<String, Object> aggregatedBtcusdtPrices = cryptoPriceService.fetchestPriceAndSize(
    			constants.CONSTANTS_BINANCE_BTCUSDT_URL, constants.CONSTANTS_BTCUSDT_L_NAME);
    	
    	cryptoPriceService.storeBestPriceAndSize(aggregatedEthustPrices, constants.CONSTANTS_ETHUSDT_NAME);
    	cryptoPriceService.storeBestPriceAndSize(aggregatedBtcusdtPrices, constants.CONSTANTS_BTCUSDT_NAME);
    }
}
