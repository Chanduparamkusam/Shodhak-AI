package com.ecomguard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class EcomguardProApplication {
    public static void main(String[] args) {
        SpringApplication.run(EcomguardProApplication.class, args);
    }
        @Bean
        @Autowired
        public RestTemplate restTemplate() {
        	return new RestTemplate();
        }
    
}
