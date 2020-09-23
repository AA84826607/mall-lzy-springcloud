package com.lzy.springcloud.item;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class MainItemWeb {
    public static void main(String[] args) {
        SpringApplication.run(MainItemWeb.class,args);
    }
}
