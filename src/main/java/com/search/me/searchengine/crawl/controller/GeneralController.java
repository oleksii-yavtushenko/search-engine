package com.search.me.searchengine.crawl.controller;

import com.search.me.searchengine.crawl.crawler.GeneralCrawler;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;

@Component
@Slf4j
public class GeneralController implements Controller {

    private CrawlController crawlController;
    private final ExecutorService executorService;

    @Autowired
    public GeneralController(ExecutorService executorService) {
        this.executorService = executorService;
        start();
    }

    @Override
    public void start() {
        try {
            String crawlStorageFolder = "/data/crawl/root";
            int numberOfCrawlers = 7;

            CrawlConfig config = new CrawlConfig();
            config.setCrawlStorageFolder(crawlStorageFolder);

            // Instantiate the controller for this crawl.
            PageFetcher pageFetcher = new PageFetcher(config);
            RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
            RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
            crawlController = new CrawlController(config, pageFetcher, robotstxtServer);

            // For each crawl, you need to add some seed urls. These are the first
            // URLs that are fetched and then the crawler starts following links
            // which are found in these pages
            crawlController.addSeed("https://www.ics.uci.edu/~lopes/");
            crawlController.addSeed("https://www.ics.uci.edu/~welling/");
            crawlController.addSeed("https://www.ics.uci.edu/");

            // The factory which creates instances of crawlers.
            CrawlController.WebCrawlerFactory<WebCrawler> factory = GeneralCrawler::new;

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
