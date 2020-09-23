package com.lzy.springcloud.manage.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Configuration
public class getRestTemplate {
    @Bean
    @LoadBalanced
    public RestTemplate getrestTemplate(){
        return new RestTemplate();
    }
}
