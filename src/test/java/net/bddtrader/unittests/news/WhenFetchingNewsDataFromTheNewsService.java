package net.bddtrader.unittests.news;

import net.bddtrader.news.NewsItem;
import net.bddtrader.news.NewsService;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class WhenFetchingNewsDataFromTheNewsService {

    NewsService newsService = new NewsService();

    @Test
    public void shouldFindItemsForAGivenStock() {
        List<NewsItem> news = newsService.getNewsFor(Arrays.asList("AAPL"));
        assertThat(news).isNotEmpty();
        assertThat(news).allMatch(item -> item.getRelated().contains("AAPL"));
    }

    @Test
    public void shouldFindItemsForMultipleStocks() {
        List<NewsItem> news = newsService.getNewsFor(Arrays.asList("AAPL","GOOG"));
        assertThat(news).isNotEmpty();
        assertThat(news).allMatch(item -> item.getRelated().contains("AAPL") || item.getRelated().contains("GOOG"));
    }

    @Test
    public void shouldNotFindItemsForAnUnknownStock() {
        List<NewsItem> news = newsService.getNewsFor(Arrays.asList("UNKNOWN"));
        assertThat(news).isEmpty();
    }

}
