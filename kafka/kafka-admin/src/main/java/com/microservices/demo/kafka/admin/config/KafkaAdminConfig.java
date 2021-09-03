package com.microservices.demo.kafka.admin.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.admin.AdminClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.microservices.demo.appconfig.config.KafkaConfigData;

@Configuration
public class KafkaAdminConfig {
    private final KafkaConfigData kafkaConfig;

    public KafkaAdminConfig(KafkaConfigData kafkaConfig) {
    	this.kafkaConfig=kafkaConfig;
    }
    
    @Bean
    public AdminClient adminClient() {
    	Map<String,Object> map = new HashMap<>();
    	map.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG,
    			kafkaConfig.getBootstrapServers());
    	return AdminClient.create(map);
    }
}
