package com.peek.search.persistence.repository;

import com.peek.search.persistence.entity.PageRank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PageRankRepository extends JpaRepository<PageRank, Long> {

    // custom query methods, if needed
}
