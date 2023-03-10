package com.peek.search.crawl.keyword;

import java.util.List;

public interface KeywordExtractor {

    List<String> extract(String textContent, int numKeywords);
}
