package com.peek.search.crawl.controller;

import com.peek.search.crawl.config.CrawlConfiguration;
import com.peek.search.crawl.crawler.DefaultCrawler;
import com.peek.search.crawl.keyword.KeywordExtractor;
import com.peek.search.service.WebPageService;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.apache.http.message.BasicHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutorService;

@Component
@Slf4j
public class DefaultController implements Controller {

    private CrawlController crawlController;
    private final ExecutorService executorService;
    private final KeywordExtractor keywordExtractor;
    private final CrawlConfiguration crawlConfiguration;
    private final WebPageService webPageService;

    @Autowired
    public DefaultController(ExecutorService executorService,
                             KeywordExtractor keywordExtractor,
                             CrawlConfiguration crawlConfiguration,
                             WebPageService webPageService) {
        this.executorService = executorService;
        this.keywordExtractor = keywordExtractor;
        this.crawlConfiguration = crawlConfiguration;
        this.webPageService = webPageService;
        start();
    }

    @Override
    public void start() {
        try {
            String crawlStorageFolder = "/data/crawl/root";
            int numberOfCrawlers = crawlConfiguration.getCrawlersNum();

            CrawlConfig config = new CrawlConfig();
            config.setCrawlStorageFolder(crawlStorageFolder);
            config.setResumableCrawling(true);
            config.setDefaultHeaders(List.of(
                    new BasicHeader(HttpHeaders.ACCEPT_LANGUAGE, crawlConfiguration.getPageAcceptLanguage()),
                    new BasicHeader("Accept-CH", crawlConfiguration.getPageAcceptCH())
            ));

            // Instantiate the controller for this crawl.
            PageFetcher pageFetcher = new PageFetcher(config);
            RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
            RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
            crawlController = new CrawlController(config, pageFetcher, robotstxtServer);

            // For each crawl, you need to add some seed urls. These are the first
            // URLs that are fetched and then the crawler starts following links
            // which are found in these pages
            crawlController.addSeed("https://www.baeldung.com/");

            // The factory which creates instances of crawlers.
            CrawlController.WebCrawlerFactory<WebCrawler> factory = () -> new DefaultCrawler(
                    keywordExtractor,
                    crawlConfiguration,
                    webPageService);

            // Start the crawl. This is a blocking operation, meaning that your code
            // will reach the line after this only when crawling is finished.
            executorService.submit(() -> crawlController.start(factory, numberOfCrawlers));
        } catch (Exception exception) {
            log.error("Unable to start general crawl controller", exception);
            throw new RuntimeException("Unable to start general crawl controller");
        }
    }

    @Override
    public void stop() {
        crawlController.shutdown();
    }
}
