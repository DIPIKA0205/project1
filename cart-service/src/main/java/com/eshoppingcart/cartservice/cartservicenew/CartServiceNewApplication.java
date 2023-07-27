package com.eshoppingcart.cartservice.cartservicenew;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class CartServiceNewApplication {

	public static void main(String[] args) {
		SpringApplication.run(CartServiceNewApplication.class, args);
	}
	
	@Bean
	public  RestTemplate restTemplate() {
		return new RestTemplate();
	}


}
