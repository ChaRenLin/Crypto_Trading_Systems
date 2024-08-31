package com.example.cryptotradingsystem.controller;

import com.example.cryptotradingsystem.model.TransactionPO;
import com.example.cryptotradingsystem.service.TradingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trade")
public class PostController {
	@Autowired
    private TradingService tradingService;
	
	
	//http://localhost:8080/api/trade/{userName}?symbol=BTCUSDT&type=SELL&amount=5.00
    @PostMapping("/{userName}")
    public String trade(@PathVariable String userName,
                        @RequestParam String symbol,
                        @RequestParam String type,
                        @RequestParam String amount) {
        return tradingService.trade(userName, symbol, type, amount);
    }

    
}
