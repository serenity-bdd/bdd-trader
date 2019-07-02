package net.bddtrader.apitests.news;

import net.bddtrader.news.NewsController;
import net.bddtrader.news.NewsItem;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

import static net.bddtrader.config.TradingDataSource.DEV;
import static net.bddtrader.config.TradingDataSource.IEX;
import static org.assertj.core.api.Assertions.assertThat;

public class WhenReadingTheNews {

    NewsController newsController;

    @Before
    public void prepareNewsController() {
        newsController = new NewsController(IEX);
    }

    @Test
    public void shouldFindTheLatestNewsAboutAParticularStockFromStaticData() {

        newsController = new NewsController(DEV);

        List<NewsItem> news = newsController.newsFor("AAPL");

        assertThat(news).isNotEmpty();

        news.forEach(
                newsItem -> {assertThat(newsItem.getRelated()).contains("AAPL");}
        );
    }

    @Test
    public void shouldFindTheLatestNewsAboutAParticularStock() {

        List<NewsItem> news = newsController.newsFor("AAPL");

        assertThat(news).isNotEmpty();

        news.forEach(
                newsItem -> {assertThat(newsItem.getRelated()).contains("AAPL");}
        );
    }
}
