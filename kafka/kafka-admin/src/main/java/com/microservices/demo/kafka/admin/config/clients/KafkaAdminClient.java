package com.microservices.demo.kafka.admin.config.clients;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicListing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.retry.RetryContext;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import com.microservices.demo.appconfig.config.KafkaConfigData;
import com.microservices.demo.appconfig.config.RetryConfigData;


@Component
public class KafkaAdminClient {
  private final Logger log = LoggerFactory.getLogger(getClass());
  
  private final RetryTemplate retryTemplate;
  private final AdminClient adminClient;
  private final RetryConfigData retryConfigData;
  private final KafkaConfigData kafkaConfigData;
  private final WebClient webClient;
  public KafkaAdminClient(RetryTemplate retryTemplate,
		  AdminClient adminClient,RetryConfigData retryConfigData
		  ,KafkaConfigData kafkaConfigData
		  ,WebClient webClient) {
	  this.retryTemplate=retryTemplate;
	  this.adminClient=adminClient;
	  this.retryConfigData=retryConfigData;
	  this.kafkaConfigData=kafkaConfigData;
	  this.webClient=webClient;
  }
  
  public void checkSchemaReg() {
	  int retryCount=1;
		int maxRetry=retryConfigData.getMaxAttempts();
		Integer multiplier=(int)retryConfigData.getMultiplier();
		long sleepTime = retryConfigData.getSleepTimeMs();
		while(!getSchemaRegStatus().is2xxSuccessful()) {
		  checkMaxRetry(retryCount, maxRetry);	
		  sleep(sleepTime);
		  sleepTime*=multiplier;		  
		}
  }
  private HttpStatus getSchemaRegStatus() {
	 try {
		return webClient.method(HttpMethod.GET)
		           .uri(kafkaConfigData.getSchemaRegistryUrl())
		           .exchange()
		           .map(ClientResponse::statusCode)
		           .block();
	} catch (Exception e) {
		e.printStackTrace();
		return HttpStatus.SERVICE_UNAVAILABLE;
	}
	 
  }
  
  public void createTopics() {
	  CreateTopicsResult createTopicsResult;
	  try {
	  createTopicsResult= retryTemplate.execute(this::doCreateTopics);
	  }catch(Throwable t) {
		  log.error("Error while creating topics {}",t.getLocalizedMessage());
	  }
	  checkTopicList();
  }
  
  public void checkTopicList() {
		Collection<TopicListing> topics= getTopics();
		int retryCount=1;
		int maxRetry=retryConfigData.getMaxAttempts();
		Integer multiplier=(int)retryConfigData.getMultiplier();
		long sleepTime = retryConfigData.getSleepTimeMs();
		for(String topic: kafkaConfigData.getTopicNamesToCreate())
		{
			while(!isTopicsCreated(topics,topic)) {
				checkMaxRetry(retryCount++,maxRetry);
				sleep(sleepTime);
				sleepTime*=multiplier;
				topics=getTopics();				
			}
		}
  }  
  private void sleep(long sleepTime) {
   try {
	Thread.sleep(sleepTime);
} catch (InterruptedException e) {
	e.printStackTrace();
}	
}

private void checkMaxRetry(int i, int maxRetry) {
  if(i>maxRetry) {
	  throw new RuntimeException("Max retry attempt reached");
  }	
  
}

private boolean isTopicsCreated(Collection<TopicListing> topics, String topic) {

	if(topics==null) {
		return false;
	}
	return topics.stream().anyMatch(t->t.name().equals(topic));
}

private CreateTopicsResult doCreateTopics(RetryContext retrycontext) {
	  List<String> topicsToBeCreatedList = kafkaConfigData.getTopicNamesToCreate();
	  List<NewTopic> kafkaTopics= topicsToBeCreatedList.stream()
	                       .map(t->new NewTopic(t,
	                    		   kafkaConfigData.getNumOfPartitions(),
	                    		   kafkaConfigData.getReplicationFactor()) )
	                       .collect(Collectors.toList());
	return adminClient.createTopics(kafkaTopics);
}

  private Collection<TopicListing> getTopics(){
	  Collection<TopicListing> topics = null;
	  try {
		  topics = retryTemplate.execute(this::doGetTopics);
	  }catch(Throwable t) {
		  log.error("error while getting topics {}",t.getMessage());
	  }
	  return topics;
  }

private Collection<TopicListing> doGetTopics(RetryContext retryContext)
		throws InterruptedException, ExecutionException {
	log.info("Reading kafka topic {},attempt {}",
			  kafkaConfigData.getTopicNamesToCreate().toArray()
			  ,retryContext.getRetryCount());
	Collection<TopicListing> topics = adminClient.listTopics().listings().get();
	if(topics!=null) {
		topics.forEach(topic->log.debug("Topic with name {} ",topic.name()));
	}
	return topics;
}
 

}
