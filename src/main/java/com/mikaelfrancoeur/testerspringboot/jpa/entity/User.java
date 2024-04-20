package com.mikaelfrancoeur.testerspringboot.jpa.entity;

import java.util.Collection;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    private Long id;

    private String firstName;
    private String lastName;

    @OneToMany(mappedBy = "authorId")
    private Collection<Post> posts;
}
