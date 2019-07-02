package net.bddtrader.tradingdata.services;

import net.bddtrader.news.NewsItem;
import net.bddtrader.portfolios.Trade;
import net.bddtrader.stocks.TopStock;
import net.bddtrader.tradingdata.TradingDataAPI;
import net.bddtrader.tradingdata.exceptions.IllegalPriceManiuplationException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;
import static org.springframework.http.HttpMethod.GET;

public class IEXtradingAPI implements TradingDataAPI {

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public List<NewsItem> getNewsFor(String stockid) {
        return new StaticAPI().getNewsFor(stockid);
    }

    @Override
    public Double getPriceFor(String stockid) {

        if (stockid.equals(Trade.CASH_ACCOUNT)) {
            return 0.01;
        }

        ResponseEntity<Double> response =
                restTemplate.exchange("https://api.iextrading.com/1.0/stock/{stockid}/price",
                        GET, null,
                        new ParameterizedTypeReference<Double>() {},
                        stockid);
        return response.getBody();
    }

    @Override
    public List<String> getPopularStocks() {

        ResponseEntity<List<TopStock>> response =
                restTemplate.exchange("https://api.iextrading.com/1.0/tops",
                        GET, null,
                        new ParameterizedTypeReference<List<TopStock>>() {});

        return requireNonNull(response.getBody()).stream()
                                                 .map(TopStock::getSymbol)
                                                 .collect(Collectors.toList());
    }

    @Override
    public void updatePriceFor(String stockid, Double currentPrice) {
        throw new IllegalPriceManiuplationException("Attempt to update prices in production");
    }

    @Override
    public void reset() {}
}
