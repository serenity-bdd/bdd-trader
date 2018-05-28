package net.bddtrader.tradingdata.services;

import net.bddtrader.tradingdata.TradingDataAPI;
import net.bddtrader.news.NewsItem;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.springframework.http.HttpMethod.GET;

public class IEXtradingAPI implements TradingDataAPI {
    @Override
    public List<NewsItem> getNewsFor(String stockid) {

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<List<NewsItem>> response =
                restTemplate.exchange("https://api.iextrading.com/1.0/stock/{stockid}/news",
                        GET, null,
                        new ParameterizedTypeReference<List<NewsItem>>() {},
                        stockid);
        return response.getBody();
    }
}
