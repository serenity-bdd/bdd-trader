package net.bddtrader.news;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import net.bddtrader.testdata.JsonTestData;
import net.bddtrader.tradingdata.NewsReader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewsService implements NewsReader {

    private final ObjectMapper mapper;

    public NewsService() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }

    @Override
    public List<NewsItem> getNewsFor(List<String> stockids) {
        File jsonInput = JsonTestData.from("news.json");
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
}
