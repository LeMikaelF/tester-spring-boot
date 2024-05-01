package com.mikaelfrancoeur.testerspringboot.cache;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@Import({ CacheConfig.class, BeanWithCache.class })
@ImportAutoConfiguration(CacheAutoConfiguration.class)
public class BeanWithCacheTest implements WithAssertions {

    @Autowired
    private BeanWithCache beanWithCache;

    @Test
    void beanWithCacheUsesCache() {
        String message1 = beanWithCache.getMessage(new BeanWithCache.Descriptor("Hello"));
        String message2 = beanWithCache.getMessage(new BeanWithCache.Descriptor("Hello"));

        assertThat(message1).isEqualTo(message2);

        String message3 = beanWithCache.getMessage(new BeanWithCache.Descriptor("Goodbye"));
        assertThat(message1).isNotEqualTo(message3);
    }
}
