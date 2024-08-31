package com.example.cryptotradingsystem.service;

import com.example.cryptotradingsystem.model.CryptoPricePO;
import com.example.cryptotradingsystem.model.TransactionPO;
import com.example.cryptotradingsystem.model.UserPO;
import com.example.cryptotradingsystem.repository.TransactionRepository;
import com.example.cryptotradingsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TradingService {

    @Autowired
    private CryptoPriceService cryptoPriceService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional
    public String trade(String userName, String symbol, String type, String amount) {
        // Get the latest price
    	// not need minus stock, since always get from APIs
    	Double amounts = Double.valueOf(amount);
    	
    	if (amounts <= 0.00)
    		return "Invalid amount.";
    	
        CryptoPricePO cryptoPricePO = cryptoPriceService.getLatestBestPrice(symbol);
        if (cryptoPricePO == null) {
            return "Price not available";
        }

        // Get the userPO
        UserPO userPO = userRepository.findByUsername(userName).orElse(null);
        if (userPO == null) {
            return "user not found";
        }

        Double tradePrice;
        Double totalCost;
        
        if (type.equalsIgnoreCase("BUY")) {
        	System.out.println("asd");
        	System.out.println(Double.valueOf(cryptoPricePO.getBestAskPrice()));
            tradePrice = Double.valueOf(cryptoPricePO.getBestAskPrice());
            totalCost = tradePrice * amounts;
            if (userPO.getBalance() < totalCost) {
                return "Insufficient balance";
            }
            
            userPO.setBalance(userPO.getBalance() - totalCost);
            
            
        } else if (type.equalsIgnoreCase("SELL")) {
            tradePrice = Double.valueOf(cryptoPricePO.getBestBidPrice());
            totalCost = tradePrice * amounts;
            userPO.setBalance(userPO.getBalance() + totalCost);
            
        } else {
            return "Invalid trade type";
        }

       
        TransactionPO transactionPO = new TransactionPO();
        transactionPO.setUserPO(userPO);
        transactionPO.setSymbol(symbol);
        transactionPO.setType(type.toUpperCase());
        transactionPO.setPrice(tradePrice);
        transactionPO.setAmount(amounts);
        transactionPO.setTimestamp(LocalDateTime.now());
        transactionRepository.save(transactionPO);

       
        userRepository.save(userPO);

        return "Trade successful";
    }

    public Double getUserBalance(String userName) {
        UserPO userPO = userRepository.findByUsername(userName).orElse(null);
        return (userPO != null) ? userPO.getBalance() : null;
    }

    public List<TransactionPO> getUserTransactionHistory(String username) {
        return transactionRepository.findByUserPO_Username(username);
    }
}
