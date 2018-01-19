package com.dqr.binance.message;

import com.binance.api.client.domain.event.AggTradeEvent;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.errors.TimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.Future;

public class AggTradeEventProducer {
	
	final static Logger logger = LoggerFactory.getLogger( AggTradeEventProducer.class );
	
	// private Producer< String, AggTradeEvent > producer;
	private Producer< String, String > producer;
	
	public AggTradeEventProducer() {
		// Empty
	}
	
	public void init() {
		Properties props = new Properties();
		//props.put( "bootstrap.servers", "localhost:9092" );
		props.put( "bootstrap.servers", "192.168.0.4:9092" );
		props.put( "acks", "all" );
		// props.put( "acks", "0" );
		props.put( "retries", 0 );
		props.put( "batch.size", 16384 );
		props.put( "linger.ms", 1 );
		props.put( "buffer.memory", 33554432 );
		props.put( "key.serializer", "org.apache.kafka.common.serialization.StringSerializer" );
		props.put( "value.serializer", "org.apache.kafka.common.serialization.StringSerializer" );
		
		producer = new KafkaProducer<>( props );
	}
	
	public void close() {
		producer.close();
	}
	
	public void produce( AggTradeEvent pAppTradeEvent ) {
		Future< RecordMetadata > future = null;
		try {
			future = producer.send( new ProducerRecord<>( "AggTradeEventTopic", pAppTradeEvent.toString() ) );
			System.out.println( "Sent Event: " + pAppTradeEvent.toString() );
			System.out.println( "Future: " + future.get()
			                                       .toString() );
			if( future.isDone() ) {
				System.out.println( "Return Future1: " + future.get()
				                                               .toString() );
			}
			else if( future.isCancelled() ) {
				System.out.println( "Return Future canceled: " + future.isCancelled() );
			}
			else {
				System.out.println( "Return Future2: " + future.get()
				                                               .toString() );
			}
		}
		catch( TimeoutException te ) {
			logger.debug("TimeoutException: " + te.getLocalizedMessage() );
		}
		catch( Exception e ) {
			logger.debug( "Exception: " + e.getLocalizedMessage() );
			//			e.printStackTrace();
			//			close();
		}
	}
}
