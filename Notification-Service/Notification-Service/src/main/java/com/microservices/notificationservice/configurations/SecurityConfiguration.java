package com.microservices.notificationservice.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain httpSecurity(HttpSecurity http) throws Exception {
        return http
                .csrf((config) -> config.disable())
                .formLogin((config) -> config.disable())
                .sessionManagement((config)->config.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic((config)->config.disable())
                .authorizeHttpRequests((req) -> req.requestMatchers("/internal/**","/notifications/health", "/swagger-ui/**", "/actuator/**", "/v3/api-docs/**").permitAll().anyRequest().authenticated())
                .build();
    }

}
