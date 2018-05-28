package net.bddtrader.stocks;

import net.bddtrader.config.TraderConfiguration;
import net.bddtrader.config.TradingDataSource;
import net.bddtrader.news.NewsItem;
import net.bddtrader.tradingdata.TradingData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StockController {

    private final TradingDataSource tradingDataSource;

    public StockController(TradingDataSource tradingDataSource) {
        this.tradingDataSource = tradingDataSource;
    }
    @Autowired
    public StockController(TraderConfiguration traderConfiguration) {
        this(traderConfiguration.getTradingDataSource());
    }

    @RequestMapping("/stock/{stockid}/price")
    public Double priceFor(@PathVariable String stockid) {
        return TradingData.instanceFor(tradingDataSource).getPriceFor(stockid);
    }

    @RequestMapping(value = "/stock/{stockid}/price", method = RequestMethod.POST)
    public Double updatePriceFor(@PathVariable String stockid, @RequestBody Double currentPrice) {
        return TradingData.instanceFor(tradingDataSource).updatePriceFor(stockid, currentPrice);
    }

}
