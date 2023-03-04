package com.peek.search.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "PageKeywords")
public class PageKeyword {

    @EmbeddedId
    private PageKeywordId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("pageId")
    private Page page;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("keywordId")
    private Keyword keyword;

    // constructors, getters, setters, etc.
}
