package com.peek.search.service;

import com.peek.search.persistence.entity.Page;
import com.peek.search.persistence.entity.PageRank;
import com.peek.search.persistence.repository.PageRankRepository;
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

    public static final int CONTENT_LENGTH = 200;

    private final PageRepository pageRepository;
    private final PageRankRepository pageRankRepository;

    @Override
    public List<SearchResultDto> search(String searchQuery) {
        var stringKeywords = Arrays.stream(searchQuery.trim()
                        .toLowerCase()
                        .split("\\s+"))
                .toList();

        var pages = pageRepository.findPagesByAllKeywords(stringKeywords, stringKeywords.size());

        pages.forEach(p -> p.setRank(pageRankRepository.findByPageId(p.getId()).get()));

        var result = pages
                .stream()
                .sorted((p1, p2) -> p2.getId() - p1.getId())
                .sorted((p1, p2) -> (int) (p2.getRank().getRank() - p1.getRank().getRank()))
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
                page.setShowcaseContent(String.format("...%s...", content));
            } else {
                page.setShowcaseContent("");
            }
        }

        return result;
    }

    @Override
    public void rankUp(String pageUrl) {
        Optional<Page> page = pageRepository.findPageByUrl(pageUrl);
        Optional<PageRank> optionalPageRank = pageRankRepository.findByPageId(page.get().getId());
        PageRank pageRank = optionalPageRank.orElseGet(PageRank::new);
        pageRank.setRank(pageRank.getRank() + 1);
        pageRankRepository.save(pageRank);
    }
}
