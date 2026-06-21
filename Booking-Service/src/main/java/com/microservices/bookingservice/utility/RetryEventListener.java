package com.microservices.bookingservice.utility;

import io.github.resilience4j.retry.RetryRegistry;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RetryEventListener {

    private static final Logger logger =
            LoggerFactory.getLogger(
                    RetryEventListener.class
            );

    private final RetryRegistry retryRegistry;

    public RetryEventListener(
            RetryRegistry retryRegistry
    ) {
        this.retryRegistry = retryRegistry;
    }

    @PostConstruct
    public void registerRetryEvents() {

        retryRegistry.getAllRetries()
                .forEach(retry -> {

                    retry.getEventPublisher()

                            .onRetry(event ->
                                    logger.info(
                                            "[{}] Retry Attempt {}",
                                            event.getName(),
                                            event.getNumberOfRetryAttempts()
                                    ))

                            .onSuccess(event ->
                                    logger.info(
                                            "[{}] Retry Successful",
                                            event.getName()
                                    ))

                            .onError(event ->
                                    logger.info(
                                            "[{}] Retry Exhausted",
                                            event.getName()
                                    ));
                });
    }
}