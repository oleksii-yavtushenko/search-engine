package com.peek.search.crawl.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Data
@Component
@ConfigurationProperties(prefix = "crawl")
public class CrawlConfiguration {

    private int crawlersNum = 1;
    private int keywordExtractNum = 1000;
    private String pageAcceptLanguage;
    private String pageAcceptCH;
}
