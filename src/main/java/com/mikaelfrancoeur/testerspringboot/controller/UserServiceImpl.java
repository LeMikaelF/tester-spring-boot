package com.mikaelfrancoeur.testerspringboot.controller;

import org.springframework.stereotype.Component;

// dummy
@Component
class UserServiceImpl implements UserService {
    @Override
    public User upsert(User id) {
        return null;
    }
}
