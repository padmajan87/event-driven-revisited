package com.microservices.demo.twitter.to.kafka.service.runner.impl;

import java.util.Arrays;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.microservices.demo.appconfig.config.TwitterToConfigServerConfigData;
import com.microservices.demo.twitter.to.kafka.service.listener.TwitterKafkaStatusListener;
import com.microservices.demo.twitter.to.kafka.service.runner.StreamRunner;

import twitter4j.FilterQuery;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

@Component
public class StreamRunnerImpl implements StreamRunner{
    private static final Logger log 
    = LoggerFactory.getLogger(StreamRunnerImpl.class);
	
    private final TwitterToConfigServerConfigData twitterConfig;
	private final TwitterKafkaStatusListener statusListen;
	
	private TwitterStream twitterStream;
	
	public StreamRunnerImpl(
			TwitterToConfigServerConfigData twitterConfig
			,TwitterKafkaStatusListener statusListen) {
		this.twitterConfig=twitterConfig;
		this.statusListen=statusListen;
	}
	
	@Override
	public void start() throws TwitterException {
		twitterStream=new TwitterStreamFactory().getInstance();
		twitterStream.addListener(statusListen);
		addFilter();
	}
	
	@PreDestroy
	public void shutDown() {
		if(twitterStream!=null) {
			log.info("Closing Twitter stream");
			twitterStream.shutdown();
		}
	}

	private void addFilter() {
		String[] keywords = twitterConfig.getTwitterKeywords().toArray(new String[0]);
		log.info("keywords extracted {}",Arrays.toString(keywords));
		FilterQuery query =new FilterQuery(keywords);
		twitterStream.filter(query);
		log.info("started filtering");
	}

}
