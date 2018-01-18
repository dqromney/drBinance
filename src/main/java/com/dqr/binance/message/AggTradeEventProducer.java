package com.dqr.binance.message;

import com.binance.api.client.domain.event.AggTradeEvent;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class AggTradeEventProducer {
	
	private Properties props;
	
	public AggTradeEventProducer() {
		props = new Properties();
		props.put( "bootstrap.servers", "localhost:9092" );
		props.put( "acks", "all" );
		props.put( "retries", 0 );
		props.put( "batch.size", 16384 );
		props.put( "linger.ms", 1 );
		props.put( "buffer.memory", 33554432 );
		props.put( "key.serializer", "org.apache.kafka.common.serialization.StringSerializer" );
		props.put( "value.serializer", "org.apache.kafka.common.serialization.StringSerializer" );
	}
	
	public void init() {
	}
	
	public void produce( AggTradeEvent pAppTradeEvent ) {
		Producer< String, AggTradeEvent > producer = null;
		try {
			producer = new KafkaProducer<>( props );
			
			for( int i = 0; i < 100; i++ ) {
				String msg = "Message " + i;
				producer.send( new ProducerRecord< String, AggTradeEvent >( "HelloKafkaTopic", pAppTradeEvent ) );
				System.out.println( "Sent:" + pAppTradeEvent.toString() );
			}
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
		finally {
			producer.close();
		}
	}
}
