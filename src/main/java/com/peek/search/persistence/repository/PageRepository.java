package com.peek.search.persistence.repository;

import com.peek.search.persistence.entity.Keyword;
import com.peek.search.persistence.entity.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PageRepository extends JpaRepository<Page, Long> {

    Optional<Page> findPageByUrl(String url);
    List<Page> findAllByKeywordsIn(List<Keyword> keywords);
}
