package com.dqr.binance;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiWebSocketClient;
import com.binance.api.client.domain.market.CandlestickInterval;
import lombok.extern.java.Log;

import java.io.IOException;

@Log
public class Main {
    public static void main(String[] args) throws IOException {
        BinanceApiWebSocketClient client = BinanceApiClientFactory.newInstance().newWebSocketClient();
    
        // Listen for aggregated trade events for ETH/BTC
        client.onAggTradeEvent("ethbtc", response -> System.out.println(response));
    
        // Listen for changes in the order book in ETH/BTC
        client.onDepthEvent("ethbtc", response -> System.out.println(response));
    
        // Obtain 1m candlesticks in real-time for ETH/BTC
        client.onCandlestickEvent( "ethbtc", CandlestickInterval.ONE_MINUTE, response -> System.out.println( response));
    }
}
