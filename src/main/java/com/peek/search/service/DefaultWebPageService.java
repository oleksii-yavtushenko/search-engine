package com.peek.search.service;

import com.peek.search.persistence.entity.Keyword;
import com.peek.search.persistence.entity.Page;
import com.peek.search.persistence.repository.KeywordRepository;
import com.peek.search.persistence.repository.PageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class DefaultWebPageService implements WebPageService {

    private final PageRepository pageRepository;
    private final KeywordRepository keywordRepository;

    /**
     * {@inheritDoc}
     */
    public void saveWebPage(String url, String content, String title, List<String> keywords) {
        // Find existing Page entity by url (or instantiate a new one) and set its attributes
        Optional<Page> optionalPage = pageRepository.findPageByUrl(url);
        Page page = optionalPage.orElse(new Page());

        page.setUrl(url);
        page.setTitle(title);
        page.setContent(content);

        // Save the Page entity using the PageRepository
        Page savedPage = pageRepository.save(page);

        // For each keyword, find existing Keyword entity (or instantiate a new one) and set its attributes
        keywords.parallelStream().forEach(k -> {
            Optional<Keyword> optionalKeyword = keywordRepository.findKeywordByKeyword(k);
            Keyword kw = optionalKeyword.orElse(new Keyword());

            kw.setKeyword(k);
            kw.setPages(Set.of(savedPage));

            // Save the Keyword entity using the KeywordRepository
            keywordRepository.save(kw);
        });
    }
}