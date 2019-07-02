package net.bddtrader.tradingdata.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.collect.Lists;
import net.bddtrader.news.NewsItem;
import net.bddtrader.portfolios.Trade;
import net.bddtrader.tradingdata.TradingDataAPI;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.google.common.collect.Lists.newArrayList;

public class StaticAPI implements TradingDataAPI {

    private final ObjectMapper mapper;
    Map<String, Double> stockPrices;

    public StaticAPI() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        stockPrices = loadSamplePrices();
    }

    @Override
    public List<NewsItem> getNewsFor(String stockid) {
        File jsonInput = testDataFrom("news.json");
        try {
            List<NewsItem> items = mapper.readValue(jsonInput, new TypeReference<List<NewsItem>>(){});
            return items.stream()
                    .filter( item -> item.getRelated().contains(stockid))
                    .collect(Collectors.toList());


        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


    private Map<String, Double> loadSamplePrices() {
        File jsonInput = testDataFrom("prices.json");
        try {
            Map<String, Map<String, Double>> samplePrices = mapper.readValue(jsonInput, new TypeReference<Map<String, Map<String, Double>>>(){});
            return samplePrices.getOrDefault("marketPrices", new HashMap<>());
        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    @Override
    public Double getPriceFor(String stockid) {
        if (stockid.equals(Trade.CASH_ACCOUNT)) {
            return 0.01;
        }
        return stockPrices.getOrDefault(stockid, 100.00);
    }

    @Override
    public List<String> getPopularStocks() {
        return newArrayList(loadSamplePrices().keySet());
    }

    @Override
    public void updatePriceFor(String stockid, Double currentPrice) {
        stockPrices.put(stockid, currentPrice);
    }

    private File testDataFrom(String source) {
        return new File(this.getClass().getResource("/sample_data/" + source).getPath());
    }

    @Override
    public void reset() {
        stockPrices = loadSamplePrices();
    }
}
