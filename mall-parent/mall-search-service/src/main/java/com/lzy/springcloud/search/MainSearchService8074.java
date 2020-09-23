package com.lzy.springcloud.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableEurekaClient
@MapperScan(basePackages = "com.lzy.springcloud.search.mapper")
public class MainSearchService8074 {
    public static void main(String[] args) {
        SpringApplication.run(MainSearchService8074.class,args);
    }
}
