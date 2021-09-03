package com.microservices.demo.appconfig.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="kafka-producer-config")
public class KafkaProducerConfigData {
	private String kerSerializerClass;
	private String valueSerializerClass;
	private String compressionType;
	private String acks;
	private Integer batchSize;
	private Integer batchSizeBoostFactor;
	private Integer lingerMs;
	private Integer requestTimeoutMs;
	private Integer retryCount;
	public String getKerSerializerClass() {
		return kerSerializerClass;
	}
	public void setKerSerializerClass(String kerSerializerClass) {
		this.kerSerializerClass = kerSerializerClass;
	}
	public String getValueSerializerClass() {
		return valueSerializerClass;
	}
	public void setValueSerializerClass(String valueSerializerClass) {
		this.valueSerializerClass = valueSerializerClass;
	}
	public String getCompressionType() {
		return compressionType;
	}
	public void setCompressionType(String compressionType) {
		this.compressionType = compressionType;
	}
	public String getAcks() {
		return acks;
	}
	public void setAcks(String acks) {
		this.acks = acks;
	}
	public Integer getBatchSize() {
		return batchSize;
	}
	public void setBatchSize(Integer batchSize) {
		this.batchSize = batchSize;
	}
	public Integer getBatchSizeBoostFactor() {
		return batchSizeBoostFactor;
	}
	public void setBatchSizeBoostFactor(Integer batchSizeBoostFactor) {
		this.batchSizeBoostFactor = batchSizeBoostFactor;
	}
	public Integer getLingerMs() {
		return lingerMs;
	}
	public void setLingerMs(Integer lingerMs) {
		this.lingerMs = lingerMs;
	}
	public Integer getRequestTimeoutMs() {
		return requestTimeoutMs;
	}
	public void setRequestTimeoutMs(Integer requestTimeoutMs) {
		this.requestTimeoutMs = requestTimeoutMs;
	}
	public Integer getRetryCount() {
		return retryCount;
	}
	public void setRetryCount(Integer retryCount) {
		this.retryCount = retryCount;
	}	
}
