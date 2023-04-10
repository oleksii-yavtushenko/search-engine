package com.peek.search.service;

import com.peek.search.persistence.entity.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultSearchService implements SearchService {
    @Override
    public List<Page> search(String searchQuery) {
        return null;
    }
}
