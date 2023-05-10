package com.peek.search.service;

import com.peek.search.persistence.repository.PageRepository;
import com.peek.search.web.dto.SearchResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class DefaultSearchService implements SearchService {

    public static final int CONTENT_LENGTH = 200;

    private final PageRepository pageRepository;

    @Override
    public List<SearchResultDto> search(String searchQuery) {
        var stringKeywords = Arrays.stream(searchQuery.trim()
                        .toLowerCase()
                        .split("\\s+"))
                .toList();

        var pages = pageRepository.findPagesByAllKeywords(stringKeywords, stringKeywords.size());

        var result = pages
                .stream()
                .sorted((p1, p2) -> p2.getId() - p1.getId())
                .map(p -> new SearchResultDto(p.getId(), p.getTitle(), p.getUrl(), p.getContent()))
                .toList();

        for (var page : result) {
            int contentId = -1;
            for (var k : stringKeywords) {
                int id = page.getShowcaseContent().indexOf(k);
                if (id != -1) {
                    contentId = id;
                    break;
                }
            }
            String content;
            int endId;
            if (contentId != -1) {
                endId = Math.min(contentId + CONTENT_LENGTH, page.getShowcaseContent().length() - 1);
                content = page.getShowcaseContent().substring(contentId, endId);
            } else {
                endId = Math.min(650 + CONTENT_LENGTH, page.getShowcaseContent().length() - 1);
                content = page.getShowcaseContent().substring(650, endId);
            }
            page.setShowcaseContent(String.format("...%s...", content));
        }

        return result;
    }
}
