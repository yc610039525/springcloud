package com.github.caoyeung.application;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class EurekaServer_StartUpApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(EurekaServer_StartUpApplication.class).web(true).run(args);
    }
}
