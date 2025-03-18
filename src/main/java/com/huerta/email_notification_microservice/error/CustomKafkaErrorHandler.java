package com.huerta.email_notification_microservice.error;

import java.time.LocalDate;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.MessageListenerContainer;

import jakarta.annotation.Nonnull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
// @Component
public class CustomKafkaErrorHandler implements CommonErrorHandler {

    @Override
    public boolean handleOne(@Nonnull Exception thrownException, @Nonnull ConsumerRecord<?, ?> record,
            @Nonnull Consumer<?, ?> consumer, @Nonnull MessageListenerContainer container) {

        // Log your custom error message
        ErrorMessage errorMessage = new ErrorMessage(
                LocalDate.now(),
                "Invalid message format. Deserialization failed.",
                "Topic: " + record.topic() + ", Partition: " + record.partition() + ", Offset: " + record.offset());

        log.error("Custom Error Handler triggered: {}", errorMessage);

        // You can stop or continue processing depending on logic
        // Here: just log, continue processing
        return true;
    }

    @Override
    public boolean isAckAfterHandle() {
        return false; // Avoid unnecessary acknowledgment
    }
}
