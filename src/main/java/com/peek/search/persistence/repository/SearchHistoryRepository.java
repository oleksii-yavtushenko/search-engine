package com.peek.search.persistence.repository;

import com.peek.search.persistence.entity.SearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {

    // custom query methods, if needed
}
