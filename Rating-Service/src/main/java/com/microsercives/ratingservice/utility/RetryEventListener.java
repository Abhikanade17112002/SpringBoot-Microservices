package com.microsercives.ratingservice.utility;

import io.github.resilience4j.retry.RetryRegistry;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

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
                                            "[{}] Retry Attempt {} Occurred At Time Stamp {}",
                                            event.getName(),
                                            event.getNumberOfRetryAttempts(),
                                            LocalDateTime.now()
                                    ))

                            .onSuccess(event ->
                                    logger.info(
                                            "[{}] Retry Successful Occurred At Time Stamp {}",
                                            event.getName(),
                                            LocalDateTime.now()
                                    ))

                            .onError(event ->
                                    logger.info(
                                            "[{}] Retry Exhausted Occurred At Time Stamp {}",
                                            event.getName(),
                                            LocalDateTime.now()
                                    ));
                });
    }
}