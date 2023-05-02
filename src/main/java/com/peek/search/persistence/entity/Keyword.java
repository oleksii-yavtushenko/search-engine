package com.peek.search.persistence.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "Keywords")
@Data
@NoArgsConstructor
public class Keyword {

    public Keyword(String keyword) {
        this.keyword = keyword;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String keyword;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Page.class, cascade = { CascadeType.MERGE })
    @JoinTable(
            name = "PageKeywords",
            joinColumns = { @JoinColumn(name = "KeywordID", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "PageID", referencedColumnName = "id") }
    )
    private Set<Page> pages;
}
