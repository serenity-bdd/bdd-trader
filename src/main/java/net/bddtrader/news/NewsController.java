package net.bddtrader.news;

import io.swagger.v3.oas.annotations.Operation;
import net.bddtrader.config.TraderConfiguration;
import net.bddtrader.config.TradingDataSource;
import net.bddtrader.tradingdata.NewsReader;
import net.bddtrader.tradingdata.TradingData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class NewsController {

    private final NewsReader newsReader;

    @Autowired
    public NewsController(NewsReader newsReader) {
        this.newsReader = newsReader;
    }

    @RequestMapping(value = "/api/news", method = GET)
    @Operation(description = "Use 'market' to get market-wide news.")
    public List<NewsItem> newsFor(@RequestParam(required = false) String symbols) {
        List<String> requestedStockids;

        if (symbols == null || symbols.isEmpty()) {
            requestedStockids = new ArrayList<>();
        } else {
            requestedStockids = Arrays.stream(symbols.split(","))
                    .map(stock -> stock.trim().toUpperCase())
                    .collect(Collectors.toList());
        }
        return newsReader.getNewsFor(requestedStockids);
    }
}
