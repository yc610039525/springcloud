package com.github.caoyeung.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration  
@EnableAutoConfiguration 
@ComponentScan(basePackages="com.github.caoyeung")
//@SpringBootApplication
@EnableDiscoveryClient
//@EnableScheduling
public class Provider2_StartupApplication {
	public static void main(String[] args) {
		SpringApplication.run(Provider2_StartupApplication.class, args);
	}
}
