package com.peek.search.persistence.repository;

import com.peek.search.persistence.entity.PageKeyword;
import com.peek.search.persistence.entity.PageKeywordId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PageKeywordRepository extends JpaRepository<PageKeyword, PageKeywordId> {

    // custom query methods, if needed
}
