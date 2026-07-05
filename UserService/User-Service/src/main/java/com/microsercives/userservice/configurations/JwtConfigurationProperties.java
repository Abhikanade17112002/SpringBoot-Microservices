package com.microsercives.userservice.configurations;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtConfigurationProperties {

    private String secretKey;
    private String expiration ;

    public JwtConfigurationProperties() {
    }

    public JwtConfigurationProperties(String secretKey, String expiration) {
        this.secretKey = secretKey;
        this.expiration = expiration;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public void setExpiration(String expiration) {
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
