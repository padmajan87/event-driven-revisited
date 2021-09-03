package com.microservices.demo.appconfig.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="retry-config")
public class RetryConfigData {
 private long initialIntervalMs;
 private long maxIntervalMs;
 private double multiplier;
 private int maxAttempts;
 private long sleepTimeMs;
public long getInitialIntervalMs() {
	return initialIntervalMs;
}
public void setInitialIntervalMs(long initialIntervalMs) {
	this.initialIntervalMs = initialIntervalMs;
}
public long getMaxIntervalMs() {
	return maxIntervalMs;
}
public void setMaxIntervalMs(long maxIntervalMs) {
	this.maxIntervalMs = maxIntervalMs;
}
public double getMultiplier() {
	return multiplier;
}
public void setMultiplier(double multiplier) {
	this.multiplier = multiplier;
}
public int getMaxAttempts() {
	return maxAttempts;
}
public void setMaxAttempts(int maxAttempts) {
	this.maxAttempts = maxAttempts;
}
public long getSleepTimeMs() {
	return sleepTimeMs;
}
public void setSleepTimeMs(long sleepTimeMs) {
	this.sleepTimeMs = sleepTimeMs;
}
 
 
}
