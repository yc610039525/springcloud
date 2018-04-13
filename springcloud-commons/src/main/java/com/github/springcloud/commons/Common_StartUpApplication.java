package com.github.springcloud.commons;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


//@SpringBootApplication//组合注解 @Configuration、@EnableAutoConfiguration 、 @ComponentScan
//@SpringCloudApplication //组合注解 SpringBootApplication+EnableDiscoveryClient+EnableCircuitBreaker

//@EnableScheduling //计划任务
@Configuration  //<beans>
@EnableAutoConfiguration 
@ComponentScan(basePackages={"com.github.springcloud.commons"}/*,excludeFilters={
		@Filter(type = FilterType.REGEX,
				pattern = {"com.github.springcloud.commons.fegin"})}*/
)
public class Common_StartUpApplication {	
	public static void main(String[] args) {
		SpringApplication.run(Common_StartUpApplication.class, args);
	}
}