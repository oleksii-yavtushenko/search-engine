package com.peek.search.persistence.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "Pages")
@Data
@EqualsAndHashCode(exclude = "keywords")
@ToString(exclude = "keywords")
public class Page {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String url;

    private String title;

    private String content;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity =Keyword.class, mappedBy = "pages", cascade = CascadeType.MERGE)
    private List<Keyword> keywords;
}