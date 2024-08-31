package com.example.cryptotradingsystem;

import org.springframework.stereotype.Component;

@Component
public class Constants {
	
	
	
	// BINANCE
	public final String CONSTANTS_BID_PRICE = "bidPrice";
	public final String CONSTANTS_ASK_PRICE = "askPrice";
	public final String CONSTANTS_BID_QTY = "bidQty";
	public final String CONSTANTS_ASK_QTY = "askQty";
	public final String CONSTANTS_BINANCE_NAME = "Binance";
	public final String CONSTANTS_BINANCE_URL = "https://api.binance.com/api/v3/ticker/bookTicker";
	public final String CONSTANTS_BINANCE_ETHUSDT_URL = CONSTANTS_BINANCE_URL + "?symbol=ETHUSDT";
	public final String CONSTANTS_BINANCE_BTCUSDT_URL = CONSTANTS_BINANCE_URL + "?symbol=BTCUSDT";
	
	//HOUBO
	public final String CONSTANTS_BID = "bid";
	public final String CONSTANTS_ASK = "ask";
	public final String CONSTANTS_BID_SIZE = "bidSize";
	public final String CONSTANTS_ASK_SIZE = "askSize";
	public final String CONSTANTS_HOUBI_NAME = "HOUBI";
	public final String CONSTANTS_HOUBI_URL = "https://api.huobi.pro/market/tickers";
	public final String CONSTANTS_HOUBI_ETHUSDT_URL = CONSTANTS_HOUBI_URL + "?symbol=ethusdt";
	public final String CONSTANTS_HOUBI_BTCUSDT_URL = CONSTANTS_HOUBI_URL + "?symbol=btcusdt";
	
	// COMMON
	public final String CONSTANTS_SYMBOL = "symbol";
	public final String CONSTANTS_BEST_BID_SIZE = "bestBidSize";
	public final String CONSTANTS_BEST_BID_PRICE = "bestBidPrice";
	public final String CONSTANTS_BEST_BID_SOURCE = "bestBidSource";
	public final String CONSTANTS_BEST_ASK_SIZE = "bestAskSize";
	public final String CONSTANTS_BEST_ASK_PRICE = "bestAskPrice";
	public final String CONSTANTS_BEST_ASK_SOURCE = "bestAskSource";
	public final String CONSTANTS_DEFAULT_USER_NAME = "initial_Alice";	
	public final String CONSTANTS_ETHUSDT_NAME = "ETHUSDT";
	public final String CONSTANTS_BTCUSDT_NAME = "BTCUSDT";
	public final String CONSTANTS_ETHUSDT_L_NAME = "ethusdt";
	public final String CONSTANTS_BTCUSDT_L_NAME = "btcusdt";
	
}
