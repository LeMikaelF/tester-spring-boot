package com.mikaelfrancoeur.testerspringboot.jpa;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.mikaelfrancoeur.testerspringboot.jpa.entity.User;
import com.mikaelfrancoeur.testerspringboot.jpa.repository.UserRepository;

@Configuration
@Import(UserService.class)
@EntityScan(basePackageClasses = User.class)
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
class JpaConfiguration {
}
