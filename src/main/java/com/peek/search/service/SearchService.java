package com.peek.search.service;

import com.peek.search.persistence.entity.Page;

import java.util.List;

public interface SearchService {

    List<Page> search(String searchQuery);
}
