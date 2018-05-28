package net.bddtrader.news;

import net.bddtrader.config.TraderConfiguration;
import net.bddtrader.config.TradingDataSource;
import net.bddtrader.tradingdata.TradingData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NewsController {

    private final TradingDataSource tradingDataSource;

    public NewsController(TradingDataSource tradingDataSource) {
        this.tradingDataSource = tradingDataSource;
    }
    @Autowired
    public NewsController(TraderConfiguration traderConfiguration) {
        this(traderConfiguration.getTradingDataSource());
    }

    @RequestMapping("/stock/{stockid}/news")
    public List<NewsItem> newsFor(@PathVariable String stockid) {

        return TradingData.instanceFor(tradingDataSource).getNewsFor(stockid);
    }
}
