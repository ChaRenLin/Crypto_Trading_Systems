package com.example.cryptotradingsystem.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;

@Entity
@Data
@Table(name = "t_crypto_price_real_time_data")
public class CryptoPricePO {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String symbol;
    private String bestBidPrice;
    private String bestBidSize;
    private String bestBidFrom;
    private String bestAskPrice;
    private String bestAskSize;
    private String bestAskFrom;
    private LocalDateTime timestamp;
	

}