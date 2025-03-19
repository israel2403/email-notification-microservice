
package com.huerta.email_notification_microservice.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.huerta.core.ProductCreatedEvent;
import com.huerta.email_notification_microservice.error.NotRetryableException;
import com.huerta.email_notification_microservice.error.RetryableException;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ProductCreatedEventHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(ProductCreatedEventHandler.class);

    private final RestTemplate restTemplate;

    @KafkaListener(topics = "product-created-events-topic")
    public void handleProductCreatedEvent(@Payload final ProductCreatedEvent event,
            @Header("messageId") final String messageId,
            @Header(KafkaHeaders.RECEIVED_KEY) final String messageKey) {
        LOGGER.info("Product created event received: {}", event.getTitle() + " - with id: " + event.getProductId());
        String requestUrl = "http://localhost:8082/response/200";
        try {
            final ResponseEntity<String> response = restTemplate.exchange(requestUrl, HttpMethod.GET, null,
                    String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                LOGGER.info("Received response from a remote service: {}", response.getBody());
            }
        } catch (ResourceAccessException e) {
            LOGGER.error("Failed to send email notification: {}", e.getMessage());
            throw new RetryableException(e);
        } catch (HttpServerErrorException e) {
            LOGGER.error(e.getMessage());
            throw new NotRetryableException(e);
        } catch (Exception e) {
            LOGGER.error("Failed to send email notification: {}", e.getMessage());
            throw new NotRetryableException(e);
        }
    }
}