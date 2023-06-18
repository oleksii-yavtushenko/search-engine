package com.peek.search.web.controller;

import com.peek.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/")
    public String getSearchPage() {
        return "index";
    }

    @GetMapping("/search")
    public String search(@RequestParam("query") String query,
                         Model model) {
        var pages = searchService.search(query);
        model.addAttribute("query", query);
        model.addAttribute("pages", pages);
        return "results";
    }

    @GetMapping("/goto")
    public String rankUp(@RequestParam String pageUrl) {
        searchService.rankUp(pageUrl);
        return pageUrl;
    }
}
