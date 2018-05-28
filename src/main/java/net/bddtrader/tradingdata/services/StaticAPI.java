package net.bddtrader.tradingdata.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import net.bddtrader.tradingdata.TradingDataAPI;
import net.bddtrader.news.NewsItem;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StaticAPI implements TradingDataAPI {

    private final ObjectMapper mapper;

    public StaticAPI() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
    }

    @Override
    public List<NewsItem> getNewsFor(String stockid) {
        File jsonInput = testDataFrom("news.json");
        try {
            return mapper.readValue(jsonInput, new TypeReference<List<NewsItem>>(){});
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private File testDataFrom(String source) {
        return new File(this.getClass().getResource("/sample_data/" + source).getPath());
    }
}
