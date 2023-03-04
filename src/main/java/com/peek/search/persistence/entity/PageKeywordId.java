package com.peek.search.persistence.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class PageKeywordId implements Serializable {

    private Long pageId;

    private Long keywordId;

    // constructors, getters, setters, etc.
}
