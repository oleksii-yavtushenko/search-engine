package com.peek.search.crawl.crawler;

import com.peek.search.crawl.config.CrawlConfiguration;
import com.peek.search.crawl.keyword.KeywordExtractor;
import com.peek.search.service.WebPageService;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class DefaultCrawler extends WebCrawler {

    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|gif|jpg"
                                                                + "|png|mp3|mp4|zip|gz))$");

    private final KeywordExtractor keywordExtractor;
    private final CrawlConfiguration crawlConfiguration;
    private final WebPageService webPageService;

    /**
     * This method receives two parameters. The first parameter is the page
     * in which we have discovered this new url and the second parameter is
     * the new url. You should implement this function to specify whether
     * the given url should be crawled or not (based on your crawling logic).
     * In this example, we are instructing the crawler to ignore urls that
     * have css, js, git, ... extensions and to only accept urls that start
     * with "<a href="https://www.ics.uci.edu/">...</a>". In this case, we didn't need the
     * referringPage parameter to make the decision.
     */
    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();
        return !FILTERS.matcher(href).matches()
                && href.startsWith("https://rezka.ag/");
    }

    /**
     * This function is called when a page is fetched and ready
     * to be processed by your program.
     */
    @Override
    public void visit(Page page) {
        String url = page.getWebURL().getURL();
        log.info("URL: " + url);

        if (page.getParseData() instanceof HtmlParseData htmlParseData) {
            List<String> extractedKeywords =
                    keywordExtractor.extract(htmlParseData.getHtml(), crawlConfiguration.getKeywordExtractNum());
            String text = htmlParseData.getText();
            String html = htmlParseData.getHtml();
            Set<WebURL> links = htmlParseData.getOutgoingUrls();

            if (!extractedKeywords.isEmpty() && !text.isEmpty() && !StringUtil.isBlank(url)) {
                webPageService.saveWebPage(page.getWebURL().getURL(),
                        htmlParseData.getText(), htmlParseData.getTitle(), extractedKeywords);
            }

            log.info("Extracted keywords: " + String.join(", ", extractedKeywords));
            log.info("Num of extracted keywords: " + extractedKeywords.size());
            log.info("Text length: " + text.length());
            log.info("Html length: " + html.length());
            log.info("Number of outgoing links: " + links.size());
        }
    }
}
