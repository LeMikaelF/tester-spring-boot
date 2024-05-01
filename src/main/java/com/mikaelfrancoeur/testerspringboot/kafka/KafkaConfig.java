package com.mikaelfrancoeur.testerspringboot.kafka;

import java.util.HashMap;

import org.apache.kafka.clients.consumer.Consumer;
import org.springframework.boot.autoconfigure.kafka.DefaultKafkaConsumerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.ConsumerFactory;

import lombok.extern.slf4j.Slf4j;

@EnableKafka
@Slf4j
class KafkaConfig {

    @Bean
    @SuppressWarnings({ "rawtypes", "unchecked" })
    DefaultKafkaConsumerFactoryCustomizer defaultKafkaConsumerFactoryCustomizer() {
        return consumerFactory -> {
            consumerFactory.addListener(new ConsumerFactory.Listener() {
                @Override public void consumerAdded(String id, Consumer consumer) {
                    log.info("Consumer {} was added: {}", id, new HashMap<>(consumer.listTopics()));
                }
            });
        };
    }
}
