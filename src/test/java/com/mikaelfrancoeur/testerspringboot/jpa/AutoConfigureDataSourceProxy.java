package com.mikaelfrancoeur.testerspringboot.jpa;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.sql.DataSource;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import io.hypersistence.utils.logging.InlineQueryLogEntryCreator;
import net.ttddyy.dsproxy.listener.ChainListener;
import net.ttddyy.dsproxy.listener.DataSourceQueryCountListener;
import net.ttddyy.dsproxy.listener.logging.SLF4JQueryLoggingListener;
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(AutoConfigureDataSourceProxy.TestConfig.class)
public @interface AutoConfigureDataSourceProxy {
    class TestConfig {
        @Bean
        BeanPostProcessor datasourcePostProcessor() {
            return new BeanPostProcessor() {
                @Override public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                    if (bean instanceof DataSource originalDataSource) {
                        ChainListener listener = new ChainListener();
                        SLF4JQueryLoggingListener loggingListener = new SLF4JQueryLoggingListener();
                        loggingListener.setQueryLogEntryCreator(new InlineQueryLogEntryCreator());
                        listener.addListener(loggingListener);
                        listener.addListener(new DataSourceQueryCountListener());
                        return ProxyDataSourceBuilder
                                .create(originalDataSource)
                                .name("DS-Proxy")
                                .listener(listener)
                                .build();
                    }
                    return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
                }
            };
        }
    }
}
