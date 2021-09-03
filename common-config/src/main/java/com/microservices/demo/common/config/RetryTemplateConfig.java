package com.microservices.demo.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import com.microservices.demo.appconfig.config.RetryConfigData;

@Configuration
public class RetryTemplateConfig {

	private final RetryConfigData retryConfig;
	
	public RetryTemplateConfig(RetryConfigData retryConfig) {
		this.retryConfig=retryConfig;
	}
	
	@Bean
	public RetryTemplate retrytemplate() {
	  RetryTemplate retryTemplate = new RetryTemplate();
	  /*Defines the time interval between each retry*/
	  ExponentialBackOffPolicy expBackOff = new ExponentialBackOffPolicy();
	  expBackOff.setInitialInterval(retryConfig.getInitialIntervalMs());
	  expBackOff.setMaxInterval(retryConfig.getMaxIntervalMs());
	  expBackOff.setMultiplier(retryConfig.getMultiplier());
	  
	  retryTemplate.setBackOffPolicy(expBackOff);
	  
	  /*Defines max retry attempts*/
	  SimpleRetryPolicy srp = new SimpleRetryPolicy();
	  srp.setMaxAttempts(retryConfig.getMaxAttempts());
	  
	  retryTemplate.setRetryPolicy(srp);
	  
	  return retryTemplate;
	}
	
}
