package com.microservice.customerservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Config {

	
	/*Uncomment in case using restTemplate
	 * @Bean
	 * 
	 * @LoadBalanced public RestTemplate restTemplate() { return new RestTemplate();
	 * }
	 */

}
