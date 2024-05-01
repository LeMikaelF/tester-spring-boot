package com.mikaelfrancoeur.testerspringboot.cache;

import java.util.UUID;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class BeanWithCache {

    @Cacheable(cacheNames = "mycache", key = "#descriptor.message")
    public String getMessage(Descriptor descriptor) {
        return descriptor.message() + UUID.randomUUID();
    }

    public record Descriptor(String message) {
    }
}
