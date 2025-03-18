
package com.huerta.email_notification_microservice.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.huerta.core.ProductCreatedEvent;

@Component
public class ProductCreatedEventHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(ProductCreatedEventHandler.class);
    
    @KafkaListener(topics = "product-created-events-topic")
    public void handleProductCreatedEvent( ProductCreatedEvent event) {

        LOGGER.info("Product created event received: {}", event.getTitle());
    }
}