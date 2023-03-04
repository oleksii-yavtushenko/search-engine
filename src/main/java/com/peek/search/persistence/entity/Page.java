package com.peek.search.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Pages")
public class Page {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    private String title;

    private String content;

    // constructors, getters, setters, etc.
}