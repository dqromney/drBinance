package com.dqr.binance;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiWebSocketClient;
import com.dqr.binance.message.AggTradeEventProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Main {
    final static Logger logger = LoggerFactory.getLogger( Main.class );
    
    private AggTradeEventProducer producer = null;
    private BinanceApiWebSocketClient client;
    
    public void init(String[] args) {
        producer = new AggTradeEventProducer();
        producer.init();
        client = BinanceApiClientFactory.newInstance().newWebSocketClient();
    }
    public void egress() {
        producer.close();
    }

    public void execute() {
        // Listen for aggregated trade events for ETH/BTC
//        client.onAggTradeEvent( "ethbtc", response -> {
//            System.out.print(".");
//            producer.produce( response );
//        } );
        // Listen for aggregated trade events for BTC/USDT
        client.onAggTradeEvent( "btcusdt", response -> {
            System.out.print(".");
            producer.produce( response );
        } );
    
        // Listen for changes in the order book in ETH/BTC
        //        client.onDepthEvent("ethbtc", response -> System.out.println(response));
    
        // Obtain 1m candlesticks in real-time for ETH/BTC
        //        client.onCandlestickEvent( "ethbtc", CandlestickInterval.ONE_MINUTE, response -> System.out.println( response));
        
    }
    
    public static void main(String[] args) throws IOException {
        Main main = new Main();
        main.init( args );
        main.execute();
        main.egress();
    }
}
