package com.peek.search.service;

import com.peek.search.web.dto.SearchResultDto;

import java.util.List;

public interface SearchService {

    List<SearchResultDto> search(String searchQuery);
    void rankUp(String pageUrl);
}
