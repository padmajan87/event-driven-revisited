package com.microservices.demo.twitter.to.kafka.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.microservices.demo.twitter.to.kafka.service.init.StreamInitializer;
import com.microservices.demo.twitter.to.kafka.service.runner.StreamRunner;

@SpringBootApplication
@ComponentScan("com.microservices.demo")
public class TwitterToKafkaServiceApplication implements CommandLineRunner{
	
	private Logger log = LoggerFactory.getLogger(TwitterToKafkaServiceApplication.class);
	
	private final StreamRunner streamRunner;
    private final StreamInitializer streamInit;

	public TwitterToKafkaServiceApplication(StreamRunner streamRunner, StreamInitializer streamInit) {
		super();
		this.streamRunner = streamRunner;
		this.streamInit = streamInit;
	}
	public static void main(String[] args) {
		   SpringApplication.run(TwitterToKafkaServiceApplication.class,args);
	}

	@Override
	public void run(String... args) throws Exception {
		streamInit.init();
		streamRunner.start();
	}
	
}
