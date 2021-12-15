package com.ead.authuser;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@Log4j2
@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
public class AuthuserApplication {

    public static void main(String[] args) {

        SpringApplication.run(AuthuserApplication.class, args);

        log.info("Application: AuthUser UP");

    }

}