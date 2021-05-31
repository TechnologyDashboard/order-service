package com.techboard.orderservice.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
@EnableConfigurationProperties
public class OrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

    //Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception
    // [Request processing failed; nested exception is org.springframework.web.client.ResourceAccessException:
    // I/O error on POST request for "http://PAYMENT-SERVICE/payment/doPayment": PAYMENT-SERVICE; nested
    // exception is java.net.UnknownHostException: PAYMENT-SERVICE] with root cause
    //java.net.UnknownHostException: PAYMENT-SERVICE
    //LoadBalanced to solve the error
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
