package com.peek.search.persistence.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
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

    @Basic(fetch=FetchType.LAZY)
    private String content;

    @ManyToMany(fetch = FetchType.LAZY, targetEntity=Keyword.class, mappedBy = "pages")
    private List<Keyword> keywords;

    @OneToOne(fetch = FetchType.EAGER, targetEntity = PageRank.class, cascade = CascadeType.ALL)
    @JoinTable(
            name = "PageRank",
            joinColumns = { @JoinColumn(name = "pageId", referencedColumnName = "id") }
    )
    private PageRank rank;
}