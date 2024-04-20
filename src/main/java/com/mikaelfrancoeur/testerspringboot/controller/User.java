package com.mikaelfrancoeur.testerspringboot.controller;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

record User(
        @NotEmpty String firstName,
        @NotEmpty String lastName,
        // just an example, not actually a good regex for emails
        @Pattern(regexp = "\\w+@\\w+\\.\\w+", message = "email is not valid") String email
) {
}
