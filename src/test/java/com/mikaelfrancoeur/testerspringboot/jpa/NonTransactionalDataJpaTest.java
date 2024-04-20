package com.mikaelfrancoeur.testerspringboot.jpa;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Import({})
@EntityScan
@AutoConfigureDataJpa
@EnableJpaRepositories
@ExtendWith(SpringExtension.class)
@Target(java.lang.annotation.ElementType.TYPE)
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface NonTransactionalDataJpaTest {

    @AliasFor(annotation = EntityScan.class, attribute = "basePackageClasses")
    Class<?>[] basePackageClasses();

    @AliasFor(annotation = EnableJpaRepositories.class, attribute = "basePackageClasses")
    Class<?>[] repositories();

}
