package com.mikaelfrancoeur.testerspringboot.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MyKafkaConsumer {

    @KafkaListener(topics = "kafka-events")
    public void listen(@Payload String message, @Header("X-Correlation-Id") String correlationId) {
        log.info("Received message with correlation ID {}: {}", correlationId, message);
    }
}
