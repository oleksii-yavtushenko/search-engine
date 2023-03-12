package com.peek.search.persistence.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Set;

@Entity
@Table(name = "Keywords")
@Data
public class Keyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String keyword;

    @ManyToMany(cascade = { CascadeType.MERGE })
    @JoinTable(
            name = "PageKeywords",
            joinColumns = { @JoinColumn(name = "KeywordID") },
            inverseJoinColumns = { @JoinColumn(name = "PageID") }
    )
    private Set<Page> pages;
}
