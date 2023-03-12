package com.peek.search.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "PageRank")
@Data
public class PageRank {

    @Id
    private Integer pageId;

    private Double rank;
}