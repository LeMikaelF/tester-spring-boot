package com.mikaelfrancoeur.testerspringboot.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "posts")
public class Post {
    @Id
    private Long id;

    private Long authorId;

    private String title;
}
