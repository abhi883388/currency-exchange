package com.practice.microservices.currencyexchangeservice;

import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
public class CircuitBreakerController {

	private Logger logger = org.slf4j.LoggerFactory.getLogger(CircuitBreakerController.class);
	
	@GetMapping("/sample-api")
	//@Retry(name = "default", fallbackMethod = "hardCodedResponse")
	@CircuitBreaker(name="default", fallbackMethod = "hardCodedResponse")
	public String sampleApi() {
		logger.info("Sample API Call received");
		ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://localhost:8080/dummy", String.class);
		return forEntity.getBody();
	}
	
	
	public String hardCodedResponse(Exception ex) {
		return "fallback-response";
		
	}
}
