package com.ironhack.contactproxyservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ContactProxyServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContactProxyServiceApplication.class, args);
	}

}
