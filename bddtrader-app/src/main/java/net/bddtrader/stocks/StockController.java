package net.bddtrader.stocks;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.bddtrader.config.TraderConfiguration;
import net.bddtrader.config.TradingDataSource;
import net.bddtrader.tradingdata.TradingData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@Tag(name = "stock")
public class StockController {

    private final TradingDataSource tradingDataSource;

    public StockController(TradingDataSource tradingDataSource) {
        this.tradingDataSource = tradingDataSource;
    }

    @Autowired
    public StockController(TraderConfiguration traderConfiguration) {
        this(traderConfiguration.getTradingDataSource());
    }

    @RequestMapping(value="/api/stock/{stockid}/price", method = GET)
    @Operation(summary = "Find the price of a given stock")
    public Double priceFor(@PathVariable String stockid) {
        return TradingData.instanceFor(tradingDataSource).getPriceFor(stockid);
    }

    @RequestMapping(value = "/api/stock/{stockid}/price", method = RequestMethod.POST)
    @Operation(summary = "Update the price of a given stock in a test environment")
    public void updatePriceFor(@PathVariable String stockid, @RequestBody Double currentPrice) {
        TradingData.instanceFor(tradingDataSource).updatePriceFor(stockid, currentPrice);
    }

    @RequestMapping(value = "/api/stocks/reset", method = RequestMethod.POST)
    @Operation(summary = "Reset the prices to default values the stock in a test environment")
    public void resetTestPrices() {
        TradingData.instanceFor(tradingDataSource).reset();
    }

    @RequestMapping(value = "/api/stock/popular", method = RequestMethod.GET)
    @Operation(summary = "List high volume stocks")
    public List<String> getPopularStocks() {
        return TradingData.instanceFor(tradingDataSource).getPopularStocks();
    }
}
