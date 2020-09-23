package com.lzy.myRule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MySekfRule {
//    @Resource
//    private RestTemplate restTemplate;

    @Bean
    public IRule myRule() {
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//       MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
//
//        HttpEntity<HttpHeaders> request = new HttpEntity<>(httpHeaders, map);
//        restTemplate.postForObject("localhost:8080/",request,String.class);
        return new RandomRule();



    }

}
