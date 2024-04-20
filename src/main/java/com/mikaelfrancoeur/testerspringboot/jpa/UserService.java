package com.mikaelfrancoeur.testerspringboot.jpa;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.mikaelfrancoeur.testerspringboot.jpa.entity.Post;
import com.mikaelfrancoeur.testerspringboot.jpa.entity.User;
import com.mikaelfrancoeur.testerspringboot.jpa.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@SuppressWarnings("SameParameterValue")
public class UserService {

    private final UserRepository repository;

    @Transactional
    void updateFirstName(Long id, String newFirstName) {
        User user = repository.findById(id);
        user.setFirstName(newFirstName);
        repository.save(user);
    }

    List<String> getPostTitlesForUserIds(List<Long> ids) {
        return repository.findAllByIdIn(ids)
                .stream()
                .map(User::getPosts)
                .flatMap(Collection::stream)
                .map(Post::getTitle)
                .toList();
    }

    List<String> getPostTitlesForUserIdsWithJoinFetch(List<Long> ids) {
        return repository.findAllByIdInWithJoinFetch(ids)
                .stream()
                .map(User::getPosts)
                .flatMap(Collection::stream)
                .map(Post::getTitle)
                .toList();
    }
}
