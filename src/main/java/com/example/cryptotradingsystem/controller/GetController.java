package com.example.cryptotradingsystem.controller;


import com.example.cryptotradingsystem.model.*;
import com.example.cryptotradingsystem.service.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetController {

    @Autowired
    private CryptoPriceService cryptoPriceService;
    
    @Autowired
    private TradingService tradingService;

    //http://localhost:8080/api/price/ETHUSDT
    //http://localhost:8080/api/price/BTCUSDT
    @GetMapping("/api/price/{symbol}")
    public CryptoPricePO getBestPrice(@PathVariable String symbol) {
        return cryptoPriceService.getLatestBestPrice(symbol);
    }
    
    //http://localhost:8080/user/wallet/balance/initial_Alice
    @GetMapping("/user/wallet/balance/{userName}")
    public Double getBalance(@PathVariable String userName) {
        return tradingService.getUserBalance(userName);
    }

    //http://localhost:8080/user/history/initial_Alice
    @GetMapping("/user/history/{userName}")
    public List<TransactionPO> getTransactionHistory(@PathVariable String userName) {
        return tradingService.getUserTransactionHistory(userName);
    }

}