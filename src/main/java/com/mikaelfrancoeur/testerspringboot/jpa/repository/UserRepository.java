package com.mikaelfrancoeur.testerspringboot.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;

import com.mikaelfrancoeur.testerspringboot.jpa.entity.User;

@RepositoryDefinition(domainClass = User.class, idClass = Long.class)
public interface UserRepository {

    User findById(Long id);

    User save(User user);

    List<User> findAllByIdIn(List<Long> ids);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.posts WHERE u.id IN :ids")
    List<User> findAllByIdInWithJoinFetch(List<Long> ids);
}
