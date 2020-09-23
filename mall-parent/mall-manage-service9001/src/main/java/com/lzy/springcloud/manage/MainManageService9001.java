package com.lzy.springcloud.manage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.lzy.springcloud.manage.mapper")
@EnableEurekaClient
@EnableCircuitBreaker
public class MainManageService9001 {
    public static void main(String[] args) {
        SpringApplication.run(MainManageService9001.class,args);
    }
}
