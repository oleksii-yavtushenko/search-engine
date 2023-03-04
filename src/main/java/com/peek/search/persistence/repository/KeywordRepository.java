package com.peek.search.persistence.repository;

import com.peek.search.persistence.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeywordRepository extends JpaRepository<Keyword, Long> {

    // custom query methods, if needed
}
