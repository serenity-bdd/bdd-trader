package net.bddtrader.news;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.bddtrader.config.TraderConfiguration;
import net.bddtrader.config.TradingDataSource;
import net.bddtrader.tradingdata.TradingData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@Api("news")
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
    @ApiOperation(value = "Get news articles about a given stock",
                  notes="Use 'market' to get market-wide news.")
    public List<NewsItem> newsFor(@PathVariable String stockid) {

        return TradingData.instanceFor(tradingDataSource).getNewsFor(stockid);
    }
}
