package com.peek.search.persistence.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "SearchHistory")
public class SearchHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String searchQuery;

    private LocalDateTime searchTime;

    // constructors, getters, setters, etc.
}
