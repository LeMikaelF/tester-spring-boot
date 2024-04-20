package com.mikaelfrancoeur.testerspringboot.controller;

import jakarta.validation.Valid;

interface UserService {

    User upsert(@Valid User id);
}
