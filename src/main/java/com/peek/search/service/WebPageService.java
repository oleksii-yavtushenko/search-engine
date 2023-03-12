package com.peek.search.service;

import java.util.List;

public interface WebPageService {

    /**
     * Saves web page with its url, content and all available keywords
     *
     * @param url      url of the page
     * @param content  content of the page
     * @param title    title of the page
     * @param keywords keywords of the page
     */
    void saveWebPage(String url, String content, String title, List<String> keywords);
}
