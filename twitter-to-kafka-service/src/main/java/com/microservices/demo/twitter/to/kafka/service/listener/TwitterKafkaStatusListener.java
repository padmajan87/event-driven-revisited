package com.microservices.demo.twitter.to.kafka.service.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.microservices.demo.appconfig.config.KafkaConfigData;
import com.microservices.demo.kafka.avro.model.TwitterAvroModel;
import com.microservices.demo.kafka.producer.service.KafkaProducer;
import com.microservices.demo.twitter.to.kafka.service.transformer.TwitterStatusToAvroTransformer;

import twitter4j.Status;
import twitter4j.StatusAdapter;

@Component
public class TwitterKafkaStatusListener extends StatusAdapter{

	private static final Logger log = LoggerFactory.getLogger(TwitterKafkaStatusListener.class);
	
	private final KafkaConfigData kafkaConfigData;
	private final KafkaProducer<Long,TwitterAvroModel> kafkaProducer;
	private final TwitterStatusToAvroTransformer twitterStatus;
	
	public TwitterKafkaStatusListener(KafkaConfigData kafkaConfigData,
			KafkaProducer<Long, TwitterAvroModel> kafkaProducer, TwitterStatusToAvroTransformer twitterStatus) {
		super();
		this.kafkaConfigData = kafkaConfigData;
		this.kafkaProducer = kafkaProducer;
		this.twitterStatus = twitterStatus;
	}

	public void onStatus(Status status) {
		log.info("Twitter status with text {}",status.getText());
		TwitterAvroModel twitterAvroModel = 
				twitterStatus.getTwitterAvroModelFromStatus(status);
		kafkaProducer.send(kafkaConfigData.getTopicName()
				, twitterAvroModel.getUserId()
				, twitterAvroModel);
	}
}
