package com.dqr.binance.message;

import com.binance.api.client.domain.event.AggTradeEvent;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class AggTradeEventProducer {
	
	private Properties props;
	// private Producer< String, AggTradeEvent > producer;
	private Producer< String, String > producer;
	
	public AggTradeEventProducer() {
		props = new Properties();
		//props.put( "bootstrap.servers", "localhost:9092" );
		props.put( "bootstrap.servers", "192.168.0.4:9092" );
		props.put( "acks", "all" );
		props.put( "retries", 0 );
		props.put( "batch.size", 16384 );
		props.put( "linger.ms", 1 );
		props.put( "buffer.memory", 33554432 );
		props.put( "key.serializer", "org.apache.kafka.common.serialization.StringSerializer" );
		props.put( "value.serializer", "org.apache.kafka.common.serialization.StringSerializer" );
	}
	
	public void init() {
		producer = new KafkaProducer<>( props );
	}
	
	public void close() {
		producer.close();
	}
	
	public void produce( AggTradeEvent pAppTradeEvent ) {
		try {
			producer.send( new ProducerRecord<>( "AggTradeEventTopic", pAppTradeEvent.toString() ) );
			System.out.println( "Sent:" + pAppTradeEvent.toString() );
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
	}
}
