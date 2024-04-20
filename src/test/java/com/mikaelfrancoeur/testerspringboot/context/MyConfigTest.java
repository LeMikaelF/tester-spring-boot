package com.mikaelfrancoeur.testerspringboot.context;

import static org.assertj.core.api.InstanceOfAssertFactories.collection;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.cache.CacheManager;

class MyConfigTest implements WithAssertions {

    private ApplicationContextRunner runner;

    @BeforeEach
    void beforeEach() {
        runner = new ApplicationContextRunner()
                .withBean(MyConfig.class);
    }

    @Test
    void givenCacheEnabledIsFalse_whenCacheConfig_thenNoCacheManager() {
        runner.withPropertyValues("cache.enabled=false")
                .run(context -> {
                    assertThat(context).doesNotHaveBean("concurrentCacheManager");
                });
    }

    @Test
    void givenCacheEnabledIsTrue_whenCacheConfig_thenCacheManager() {
        runner.withPropertyValues("cache.enabled=true", "cache.names=cache1,cache2")
                .run(context -> {
                    assertThat(context).getBean(CacheManager.class)
                            .extracting(CacheManager::getCacheNames)
                            .asInstanceOf(collection(String.class))
                            .containsExactlyInAnyOrder("cache1", "cache2");
                });
    }

    @Test
    void givenCachedEnabledIsMissing_whenCacheConfig_thenNoCacheManager() {
        runner.run(context -> {
            assertThat(context).doesNotHaveBean("concurrentCacheManager");
        });
    }

    @Test
    @Disabled // doesn't work because of https://github.com/spring-projects/spring-framework/pull/32704
    void givenProfileLocal_whenCacheConfig_thenCacheManager() {
        runner.withPropertyValues("spring.profiles.active=local")
                .run(context -> {
                    assertThat(context).hasBean("concurrentCacheManager");
                });
    }

    @Test
    @Disabled // doesn't work because of https://github.com/spring-projects/spring-framework/pull/32704
    void givenProfileLocalAndCacheEnabledIsFalse_whenCacheConfig_thenCacheManager() {
        runner.withPropertyValues("spring.profiles.active=local", "cache.enabled=false")
                .run(context -> {
                    assertThat(context).hasBean("concurrentCacheManager");
                });
    }
}
