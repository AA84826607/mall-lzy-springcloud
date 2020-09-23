package com.lzy.springcloud.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import sun.applet.Main;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class MainSearchWeb {
    public static void main(String[] args) {
        SpringApplication.run(MainSearchWeb.class,args);
    }
}
