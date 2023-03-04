package com.peek.search.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Keywords")
public class Keyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String keyword;

    // constructors, getters, setters, etc.
}
