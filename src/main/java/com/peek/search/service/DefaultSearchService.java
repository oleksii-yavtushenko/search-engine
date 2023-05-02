package com.peek.search.service;

import com.peek.search.persistence.repository.KeywordRepository;
import com.peek.search.persistence.repository.PageRepository;
import com.peek.search.web.dto.SearchResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class DefaultSearchService implements SearchService {

    private final PageRepository pageRepository;
    private final KeywordRepository keywordRepository;

    @Override
    public List<SearchResultDto> search(String searchQuery) {
        var keywords = Arrays.stream(searchQuery.trim()
                        .toLowerCase()
                        .split("\\s+"))
                .map(keywordRepository::findKeywordByKeyword)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();

        var pages = pageRepository.findAllByKeywordsIn(keywords);

        return pages
                .stream()
                .map(p -> new SearchResultDto(p.getId(), p.getTitle(), p.getUrl(), p.getContent().substring(0, 20)))
                .toList();
    }
}
