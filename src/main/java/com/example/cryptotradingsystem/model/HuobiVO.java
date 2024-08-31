package com.example.cryptotradingsystem.model;


import lombok.Data;
@Data
public class HuobiVO {
	private String symbol;
    private double open;
    private double high;
    private double low;
    private double close;
    private double amount;
    private double vol;
    private int count;
    private double bid;
    private double bidSize;
    private double ask;
    private double askSize;
    
    
}
