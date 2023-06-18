package com.peek.search.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "PageRank")
@Data
public class PageRank {

    @Id
    private Integer pageId;

    private Double rank;

    @OneToOne(fetch = FetchType.LAZY, targetEntity=Page.class, mappedBy = "rank")
    private Page page;
}