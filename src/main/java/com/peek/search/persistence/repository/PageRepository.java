package com.peek.search.persistence.repository;

import com.peek.search.persistence.entity.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PageRepository extends JpaRepository<Page, Long> {

    Optional<Page> findPageByUrl(String url);

    @Query("SELECT p FROM Page p WHERE " +
            "(SELECT COUNT(DISTINCT k.keyword) FROM p.keywords k WHERE k.keyword IN :keywords) = :keywordCount")
    List<Page> findPagesByAllKeywords(List<String> keywords, int keywordCount);
}
