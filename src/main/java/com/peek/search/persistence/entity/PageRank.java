package com.peek.search.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "PageRank")
public class PageRank {

    @Id
    private Long pageId;

    private Double rank;

    // constructors, getters, setters, etc.
}