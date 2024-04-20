package com.mikaelfrancoeur.testerspringboot.context;

import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig {

    // doesn't work because of https://github.com/spring-projects/spring-framework/pull/32704
    @ConditionalOnExpression("${cache.enabled:false} || T(java.util.Arrays).asList(environment.getActiveProfiles()).contains('something')")
    static class CacheConfig {
        @Bean
        CacheManager concurrentCacheManager(@Value("#{'${cache.names}'.split(',')}") Set<String> cacheNames) {
            return new ConcurrentMapCacheManager(cacheNames.toArray(new String[0]));
        }
    }

}
