package com.peek.search.crawl.keyword;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Component
public class DefaultKeywordExtractor implements KeywordExtractor {

    private static final Set<String> STOP_WORDS = new HashSet<>(Arrays.asList("a", "an", "the", "and", "or", "of", "to", "in", "on", "at", "with", "и", "да", "или", "на", "або", "і"));

    @Override
    public List<String> extract(String textContent, int numKeywords) {
        // Step 1: Parse the HTML page and extract the text content.
        // This can be done using an HTML parser library such as JSoup.
        Document doc = Jsoup.parse(textContent);
        doc.select("script, style").remove();
        String text = doc.body().text();

        // Step 2: Clean the text content by removing HTML tags, stop words, and other irrelevant content.
        List<String> tokens = Arrays.stream(text.split("[^\\p{L}\\d]+"))
                .map(String::toLowerCase)
                .filter(o -> !STOP_WORDS.contains(o))
                .toList();

        // Step 3: Tokenize the cleaned text content into individual words.

        // Step 4: Count the frequency of each word in the tokenized text content.
        Map<String, Integer> wordFreq = new HashMap<>();
        for (String token : tokens) {
            int freq = wordFreq.getOrDefault(token, 0);
            wordFreq.put(token, freq + 1);
        }

        // Step 5: Remove any words that are not relevant keywords based on domain-specific criteria.
        wordFreq.keySet().removeIf(word -> wordFreq.get(word) < 2);

        // Step 6: Sort the remaining keywords by frequency in descending order.
        List<String> keywords = new ArrayList<>(wordFreq.keySet());
        keywords.sort((word1, word2) -> wordFreq.get(word2) - wordFreq.get(word1));

        // Step 7: Return the top N keywords as the result.
        return keywords.subList(0, Math.min(keywords.size(), numKeywords));
    }
}
