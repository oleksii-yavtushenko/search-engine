package com.peek.search.persistence.repository;

import com.peek.search.persistence.entity.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PageRepository extends JpaRepository<Page, Long> {

    // custom query methods, if needed
}
