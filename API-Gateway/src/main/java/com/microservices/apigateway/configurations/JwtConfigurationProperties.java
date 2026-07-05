package com.microservices.apigateway.configurations;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtConfigurationProperties {

    private String secretKey;
    private long expiration ;

    public JwtConfigurationProperties() {
    }

    public JwtConfigurationProperties(String secretKey, long expiration) {
        this.secretKey = secretKey;
        this.expiration = expiration;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public long getExpiration() {
        return expiration;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }

    @Override
    public String toString() {
        return "JwtConfigurationProperties{" +
                "secretKey='" + secretKey + '\'' +
                ", expiration='" + expiration + '\'' +
                '}';
    }
}
