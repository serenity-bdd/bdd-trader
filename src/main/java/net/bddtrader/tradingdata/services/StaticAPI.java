package net.bddtrader.tradingdata.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import net.bddtrader.news.NewsItem;
import net.bddtrader.portfolios.Trade;
import net.bddtrader.stocks.Company;
import net.bddtrader.stocks.TradeBook;
import net.bddtrader.tops.Top;
import net.bddtrader.tradingdata.TradingDataAPI;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
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
    public List<NewsItem> getNewsFor(List<String> stockids) {
        File jsonInput = testDataFrom("news.json");
        try {
            List<NewsItem> items = mapper.readValue(jsonInput, new TypeReference<List<NewsItem>>() {
            });
            if (stockids.isEmpty()) {
                return items;
            } else {
                return items.stream().filter(item -> isRelatedNews(item, stockids))
                        .collect(Collectors.toList());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private boolean isRelatedNews(NewsItem item, List<String> stockids) {
        return stockids.stream().anyMatch(
                stockId -> item.getRelated().contains(stockId)
        );
    }


    private Map<String, Double> loadSamplePrices() {
        File jsonInput = testDataFrom("prices.json");
        try {
            Map<String, Map<String, Double>> samplePrices = mapper.readValue(jsonInput, new TypeReference<Map<String, Map<String, Double>>>() {
            });
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
        try {
            return new File(this.getClass().getResource("/sample_data/" + source).toURI());
        } catch (URISyntaxException e) {
            throw new IllegalStateException("No test data found for " + source);
        } catch(NullPointerException npe) {
            throw new MissingTestDataFileException(source);
        }
    }

    @Override
    public void reset() {
        stockPrices = loadSamplePrices();
    }

    @Override
    public List<net.bddtrader.tradingdata.Trade> getLatestTrades() {
        return null;
    }

    @Override
    public List<Top> getTops() {
        try {
            File jsonInput = testDataFrom("tops.json");
            return mapper.readValue(jsonInput, new TypeReference<List<Top>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public TradeBook getBookFor(String stockid) {
        try {
            File jsonInput = testDataFrom("book_" + stockid.toLowerCase() + ".json");
            if (!jsonInput.exists()) {
                throw new NoSuchCompanyException(stockid);
            }
            return mapper.readValue(jsonInput, new TypeReference<TradeBook>() {});
        } catch (IOException | MissingTestDataFileException e) {
            throw new NoSuchCompanyException(stockid);
        }
    }

    @Override
    public Company getCompanyFor(String stockid) {
        File jsonInput = testDataFrom("companies.json");
        try {
            Map<String, Company> companies = mapper.readValue(jsonInput, new TypeReference<Map<String, Company>>() {
            });
            if (!companies.containsKey(stockid.toLowerCase())) {
                throw new NoSuchCompanyException("Unknown company: " + stockid);
            }
            return companies.get(stockid.toLowerCase());
        } catch (IOException e) {
            e.printStackTrace();
            throw new NoSuchCompanyException("Could not load companies list");
        }
    }
}
