package com.microservices.demo.appconfig.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="kafka-config")
public class KafkaConfigData {
   private String bootstrapServers;
   private String schemaRegistryUrlKey;
   private String schemaRegistryUrl;
   private String topicName;
   private List<String> topicNamesToCreate;
   private Integer numOfPartitions;
   private short replicationFactor;
   

public String getBootstrapServers() {
	return bootstrapServers;
}
public void setBootstrapServers(String bootstrapServers) {
	this.bootstrapServers = bootstrapServers;
}
public String getSchemaRegistryUrlKey() {
	return schemaRegistryUrlKey;
}
public void setSchemaRegistryUrlKey(String schemaRegistryUrlKey) {
	this.schemaRegistryUrlKey = schemaRegistryUrlKey;
}
public String getSchemaRegistryUrl() {
	return schemaRegistryUrl;
}
public void setSchemaRegistryUrl(String schemaRegistryUrl) {
	this.schemaRegistryUrl = schemaRegistryUrl;
}
public String getTopicName() {
	return topicName;
}
public void setTopicName(String topicName) {
	this.topicName = topicName;
}
public List<String> getTopicNamesToCreate() {
	return topicNamesToCreate;
}
public void setTopicNamesToCreate(List<String> topicNamesToCreate) {
	this.topicNamesToCreate = topicNamesToCreate;
}
public Integer getNumOfPartitions() {
	return numOfPartitions;
}
public void setNumOfPartitions(Integer numOfPartitions) {
	this.numOfPartitions = numOfPartitions;
}
public short getReplicationFactor() {
	return replicationFactor;
}
public void setReplicationFactor(short replicationFactor) {
	this.replicationFactor = replicationFactor;
}   
   
}
