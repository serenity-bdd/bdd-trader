package net.bddtrader.news;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.bddtrader.config.TraderConfiguration;
import net.bddtrader.config.TradingDataSource;
import net.bddtrader.tradingdata.TradingData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@Tag(name = "news")
public class NewsController {

    private final TradingDataSource tradingDataSource;

    public NewsController(TradingDataSource tradingDataSource) {
        this.tradingDataSource = tradingDataSource;
    }
    @Autowired
    public NewsController(TraderConfiguration traderConfiguration) {
        this(traderConfiguration.getTradingDataSource());
    }

    @RequestMapping(value="/api/stock/{stockid}/news", method = GET)
    @Operation(summary = "Get news articles about a given stock",
            description="Use 'market' to get market-wide news.")
    public List<NewsItem> newsFor(@PathVariable String stockid) {

        return TradingData.instanceFor(tradingDataSource).getNewsFor(stockid);
    }
}
