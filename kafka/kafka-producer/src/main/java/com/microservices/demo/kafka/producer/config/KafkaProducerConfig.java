package com.microservices.demo.kafka.producer.config;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import com.microservices.demo.appconfig.config.KafkaConfigData;
import com.microservices.demo.appconfig.config.KafkaProducerConfigData;

@Configuration
public class KafkaProducerConfig<K extends Serializable,V extends SpecificRecordBase>{
  
	private final KafkaProducerConfigData kafkaProdConfig;
	private final KafkaConfigData kafkaConfigData;
	
	public KafkaProducerConfig(KafkaProducerConfigData kafkaProdConfig, KafkaConfigData kafkaConfigData) {
		this.kafkaProdConfig = kafkaProdConfig;
		this.kafkaConfigData = kafkaConfigData;
	}
	
	@Bean
	public Map<String,Object> producerConfig(){
		Map<String,Object> props = new HashMap<>();
		props.put(kafkaConfigData.getSchemaRegistryUrlKey(), kafkaConfigData.getSchemaRegistryUrl());
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,kafkaConfigData.getBootstrapServers());

		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, kafkaProdConfig.getKerSerializerClass());
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, kafkaProdConfig.getValueSerializerClass());
		props.put(ProducerConfig.BATCH_SIZE_CONFIG, kafkaProdConfig.getBatchSize()*kafkaProdConfig.getBatchSizeBoostFactor());
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, kafkaProdConfig.getKerSerializerClass());
		props.put(ProducerConfig.RETRIES_CONFIG, kafkaProdConfig.getRetryCount());
		props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, kafkaProdConfig.getCompressionType());
		props.put(ProducerConfig.LINGER_MS_CONFIG,kafkaProdConfig.getLingerMs());
		props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG,kafkaProdConfig.getRequestTimeoutMs());
		props.put(ProducerConfig.ACKS_CONFIG, kafkaProdConfig.getAcks());
		return props;
	}
	@Bean
	public ProducerFactory<K,V> producerFactory(){
	  return new DefaultKafkaProducerFactory<>(producerConfig());	
	}
	
	@Bean
	public KafkaTemplate<K,V> kafkaTemplate(){
	 return new KafkaTemplate<>(producerFactory());	
	}
	
	
}
