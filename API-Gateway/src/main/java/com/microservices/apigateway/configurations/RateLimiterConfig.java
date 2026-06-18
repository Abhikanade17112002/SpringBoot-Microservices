package com.microservices.apigateway.configurations;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

//@Configuration
//public class RateLimiterConfig {
//
//    @Bean
//    public KeyResolver globalKeyResolver() {
//
//        return exchange ->
//                Mono.just("GLOBAL_KEY");
//    }
//}

@Configuration
public class RateLimiterConfig {

    @Bean
    public KeyResolver userKeyResolver() {

        return exchange ->

                Mono.justOrEmpty(

                                exchange
                                        .getRequest()
                                        .getHeaders()
                                        .getFirst(
                                                "X-USERNAME"
                                        )
                        )

                        .switchIfEmpty(
                                Mono.just("ANONYMOUS")
                        );
    }

}