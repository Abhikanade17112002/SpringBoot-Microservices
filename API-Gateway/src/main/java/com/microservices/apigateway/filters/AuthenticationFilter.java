package com.microservices.apigateway.filters;

import com.microservices.apigateway.utility.JWTUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class AuthenticationFilter implements GlobalFilter , Ordered {
    public final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final JWTUtility jwtUtility;
    private final List<String> publicEndpoints =
            List.of(
                    "/api/v1/auth/onboardcustomer",
                    "/api/v1/auth/onboardowner",
                    "/api/v1/auth/usersignin"
            );

    public AuthenticationFilter(JWTUtility jwtUtility) {
        this.jwtUtility = jwtUtility;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.info("Incoming Request In Auth Filter  ==>" + exchange.getRequest().getURI().toString());
        String path = exchange.getRequest().getURI().getPath();
        if(publicEndpoints.contains(path)) {
            return chain.filter(exchange);
        }

        String authHeader =
                exchange
                        .getRequest()
                        .getHeaders()
                        .getFirst("Authorization");
        logger.info("authHeader ==>" + authHeader);

        if(authHeader == null ||  !authHeader.startsWith("Bearer ")) {

            exchange
                    .getResponse()
                    .setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange
                    .getResponse()
                    .setComplete();
        }

        String authToken = authHeader.split(" ")[1];
        logger.info("authToken =>" + authToken);

        if( !jwtUtility.validateToken(authToken)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String username = jwtUtility.extractUsername(authToken);
        logger.info("userName ==>" + username);
        String roles = jwtUtility.extractRoles(authToken);
        logger.info("roles ==>" + roles);
        String userId = jwtUtility.extractUserId(authToken);
        logger.info("userId =>" + userId);

        ServerHttpRequest modifiedRequest =
                exchange.getRequest()
                        .mutate()
                        .header(
                                "X-USERNAME",
                                username
                        )
                        .header(
                                "X-ROLES",
                                roles
                        )
                        .header(
                                "X-USER-ID",
                                userId
                        )
                        .build();

        return chain.filter(
                exchange
                        .mutate()
                        .request(modifiedRequest)
                        .build()
        );
    }

    @Override
    public int getOrder(){
        return -1 ;
    }


}
