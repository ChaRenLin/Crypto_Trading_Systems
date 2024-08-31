package com.example.cryptotradingsystem.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import com.example.cryptotradingsystem.Constants;
import com.example.cryptotradingsystem.model.CryptoPricePO;
import com.example.cryptotradingsystem.model.HuobiVO;
import com.example.cryptotradingsystem.model.Response;
import com.example.cryptotradingsystem.repository.CryptoPriceRepository;

@Service
public class CryptoPriceService {

    @Autowired
    private CryptoPriceRepository cryptoPriceRepository;
    
    @Autowired
    private Constants constants;
    
    private RestTemplate restTemplate = new RestTemplate();

    public Map<String, Object> fetchestPriceAndSize(String binanceURL, String huobiType) {
        
        Map <String, Double> huobiPrices = new HashMap<>();
        Response huobiResponse = restTemplate.getForObject(constants.CONSTANTS_HOUBI_URL, Response.class);
        
      
        List<HuobiVO> filteredList = huobiResponse.getData().stream()
        	    .filter(huobiVO -> huobiType.equals(huobiVO.getSymbol()))
        	    .collect(Collectors.toList());
        
        
        for (HuobiVO huobiVO : filteredList) {
        	if(huobiType.equals(huobiVO.getSymbol())) {
        		huobiPrices.put(
    	        		constants.CONSTANTS_BID_PRICE, 
    	        		huobiVO.getBid());
    	        
    	        huobiPrices.put(
    	        		constants.CONSTANTS_ASK_PRICE, 
    	        		huobiVO.getAsk());
    	        
    	        huobiPrices.put(
    	        		constants.CONSTANTS_BID_QTY, 
    	        		huobiVO.getBidSize());
    	        
    	        huobiPrices.put(
    	        		constants.CONSTANTS_ASK_QTY, 
    	        		huobiVO.getAskSize());
        	}
        }
        
        
        Map <String, Double> binancePrices = new HashMap<>();
        Map<String, Object> binanceResponse = restTemplate.getForObject(binanceURL, Map.class);
        binancePrices.put(
        		constants.CONSTANTS_BID_PRICE, 
        		Double.parseDouble((String) binanceResponse.get(constants.CONSTANTS_BID_PRICE)));
        
        binancePrices.put(
        		constants.CONSTANTS_ASK_PRICE, 
        		Double.parseDouble((String) binanceResponse.get(constants.CONSTANTS_ASK_PRICE)));
        
        binancePrices.put(
        		constants.CONSTANTS_BID_QTY, 
        		Double.parseDouble((String) binanceResponse.get(constants.CONSTANTS_BID_QTY)));
        
        binancePrices.put(
        		constants.CONSTANTS_ASK_QTY, 
        		Double.parseDouble((String) binanceResponse.get(constants.CONSTANTS_ASK_QTY)));
        
        // COMPARISON, TO GET BEST PRICE AND BEST SIZE FOR BUY AND SELL
        Map<String, Map<String, Double>> allPrices = new HashMap<>();
        allPrices.put("Binance", binancePrices);
        allPrices.put("Houbi", huobiPrices);
        Map<String, Object> aggregatedPrices = calculateAggregatedPrices(allPrices);
        return aggregatedPrices; 
    }

    public void storeBestPriceAndSize(Map<String, Object> pricesResults, String symbol) {
       
        // Save to the database
        CryptoPricePO cryptoPricePO = new CryptoPricePO();
        
        Object bestBidPriceObj = pricesResults.get(constants.CONSTANTS_BEST_BID_PRICE);
        Object bestAskPriceObj = pricesResults.get(constants.CONSTANTS_BEST_ASK_PRICE);
        Object bestBidSizeObj = pricesResults.get(constants.CONSTANTS_BEST_BID_SIZE);
        Object bestAskSizeObj = pricesResults.get(constants.CONSTANTS_BEST_ASK_SIZE);
        Object bestBidSourceObj = pricesResults.get(constants.CONSTANTS_BEST_BID_SOURCE);
        Object bestAskSoruceObj = pricesResults.get(constants.CONSTANTS_BEST_ASK_SOURCE);
        
        cryptoPricePO.setSymbol(symbol);
        cryptoPricePO.setBestBidPrice(checkIfValidString(bestBidPriceObj));
        cryptoPricePO.setBestAskPrice(checkIfValidString(bestAskPriceObj));
        cryptoPricePO.setBestBidSize(checkIfValidString(bestBidSizeObj));
        cryptoPricePO.setBestAskSize(checkIfValidString(bestAskSizeObj));
        cryptoPricePO.setBestBidFrom(checkIfValidString(bestBidSourceObj));
        cryptoPricePO.setBestAskFrom(checkIfValidString(bestAskSoruceObj));
        
        cryptoPricePO.setTimestamp(LocalDateTime.now());
        cryptoPriceRepository.save(cryptoPricePO);
    }
    
    public String checkIfValidString(Object item) {
    	if (item instanceof String) {
            return item.toString();
        } else if (item != null) {
            // Handle other possible types or throw an exception
            return item.toString();
        } else {
            return "";
        }
    }
    
    
    
	public Map<String, Object> calculateAggregatedPrices(Map<String, Map<String, Double>> priceSources) {
		// Initialize the best prices, sizes, and sources
        double bestBidPrice = Double.NEGATIVE_INFINITY;
        double bestBidSize = 0.0;
        String bestBidSource = "";

        double bestAskPrice = Double.POSITIVE_INFINITY;
        double bestAskSize = 0.0;
        String bestAskSource = "";

        // Iterate through each source
        for (Map.Entry<String, Map<String, Double>> entry : priceSources.entrySet()) {
            String source = entry.getKey();
            Map<String, Double> prices = entry.getValue();

            // Update best bid price and size
            double bidPrice = prices.get(constants.CONSTANTS_BID_PRICE);
            double bidSize = prices.get(constants.CONSTANTS_BID_QTY);
            if (bidPrice > bestBidPrice || (bidPrice == bestBidPrice && bidSize > bestBidSize)) {
                bestBidPrice = bidPrice;
                bestBidSize = bidSize;
                bestBidSource = source;
            }

            // Update best ask price and size
            double askPrice = prices.get(constants.CONSTANTS_ASK_PRICE);
            double askSize = prices.get(constants.CONSTANTS_ASK_QTY);
            if (askPrice < bestAskPrice || (askPrice == bestAskPrice && askSize > bestAskSize)) {
                bestAskPrice = askPrice;
                bestAskSize = askSize;
                bestAskSource = source;
            }
        }

        // Prepare the result map
        Map<String, Object> result = new HashMap<>();
        result.put(constants.CONSTANTS_BEST_BID_PRICE, bestBidPrice);
        result.put(constants.CONSTANTS_BEST_BID_SIZE, bestBidSize);
        result.put(constants.CONSTANTS_BEST_BID_SOURCE, bestBidSource);
        result.put(constants.CONSTANTS_BEST_ASK_PRICE, bestAskPrice);
        result.put(constants.CONSTANTS_BEST_ASK_SIZE, bestAskSize);
        result.put(constants.CONSTANTS_BEST_ASK_SOURCE, bestAskSource);

        return result;
    }
    

    public CryptoPricePO getLatestBestPrice(String symbol) {
        return cryptoPriceRepository.findFirstBySymbolOrderByTimestampDesc(symbol);
    }
}
