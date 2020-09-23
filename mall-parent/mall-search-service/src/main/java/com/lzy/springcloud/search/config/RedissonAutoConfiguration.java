package com.lzy.springcloud.search.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonAutoConfiguration {
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private String port;

    @Bean
    public RedissonClient getRedisson(){
        String address="redis://"+host+":"+port;
        Config config = new Config();
        config.useSingleServer()
                .setAddress(address)
                .setReconnectionTimeout(10000)
                .setDatabase(0)
                .setConnectionPoolSize(40)
                .setTimeout(10000);
        return Redisson.create(config);
    }
}
