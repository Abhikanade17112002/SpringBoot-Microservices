package com.microsercives.userservice.configurations;


import com.microsercives.userservice.filters.JWTAuthSecurityFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableMethodSecurity
@Configuration
public class SecurityConfigurations {

    @Autowired
    @Lazy
    private JWTAuthSecurityFilter jwtAuthSecurityFilter ;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
       SecurityFilterChain securityFilterChain = httpSecurity
                .csrf(csrfConfig->csrfConfig.disable())
                .sessionManagement(sessionConfig->
                        sessionConfig.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        ))
                .authorizeHttpRequests(request->
                        request.requestMatchers("/auth/**","/swagger-ui/**" , "/actuator/**",
                 "/v3/api-docs/**").permitAll()
                                .requestMatchers("/owners/**","/customers/**").authenticated()
                                .anyRequest().authenticated()
                )
                .formLogin(formConfig->formConfig.disable())
               .httpBasic(httpConfig->httpConfig.disable())
               .addFilterBefore(jwtAuthSecurityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
       return securityFilterChain ;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {

        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder bcryptPasswordEncoder(){
        return new BCryptPasswordEncoder() ;
    }
}
