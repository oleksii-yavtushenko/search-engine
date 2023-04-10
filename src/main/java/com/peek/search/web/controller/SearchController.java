package com.peek.search.web.controller;

import com.peek.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/")
    public String getSearchPage() {
        return "index";
    }

    public String search(String searchFormat) {
        var pages = searchService.search(searchFormat);
        return "";
    }
}
