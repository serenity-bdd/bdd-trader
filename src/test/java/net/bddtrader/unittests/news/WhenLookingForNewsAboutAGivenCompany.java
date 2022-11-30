package net.bddtrader.unittests.news;

import net.bddtrader.news.NewsController;
import net.bddtrader.news.NewsItem;
import net.bddtrader.news.NewsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class WhenLookingForNewsAboutAGivenCompany {

    NewsController newsController;

    @BeforeEach
    public void prepareNewsController() {
        newsController = new NewsController(new NewsService());
    }

    @Test
    public void shouldFindTheLatestNewsAboutAParticularStock() {

        List<NewsItem> news = newsController.newsFor("AAPL");

        assertThat(news).isNotEmpty();

        news.forEach(
                newsItem -> {assertThat(newsItem.getRelated()).contains("AAPL");}
        );
    }

    @Test
    public void shouldFindTheLatestNewsAboutMultipleStocks() {

        List<NewsItem> news = newsController.newsFor("FB,GOOGL");

        assertThat(news).isNotEmpty();

        news.forEach(
                newsItem -> {assertThat(newsItem.getRelated())
                                .matches(item -> item.contains("FB") || item.contains("GOOGL"));}
        );
    }

}
