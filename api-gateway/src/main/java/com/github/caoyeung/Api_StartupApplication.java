package com.github.caoyeung;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.github.caoyeung.config.DefineFilterProcessor;
import com.netflix.zuul.FilterProcessor;

@EnableZuulProxy
@Configuration
@EnableAutoConfiguration
@ComponentScan
@EnableDiscoveryClient
public class Api_StartupApplication {
	public static void main(String[] args) {
		FilterProcessor.setProcessor(new DefineFilterProcessor());
		SpringApplication.run(Api_StartupApplication.class, args);
	}
}
