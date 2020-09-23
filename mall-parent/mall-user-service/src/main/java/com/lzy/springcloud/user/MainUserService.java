package com.lzy.springcloud.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
@MapperScan(value = "com.lzy.springcloud.user.mapper")
public class MainUserService {
    public static void main(String[] args) {
        SpringApplication.run(MainUserService.class,args);
    }
}
