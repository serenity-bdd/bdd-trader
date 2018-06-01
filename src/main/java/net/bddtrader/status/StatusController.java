package net.bddtrader.status;

import net.bddtrader.config.TraderConfiguration;
import net.bddtrader.config.TradingDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusController {

    private final TradingDataSource tradingDataSource;

    public StatusController(TradingDataSource tradingDataSource) {
        this.tradingDataSource = tradingDataSource;
    }

    @Autowired
    public StatusController(TraderConfiguration traderConfiguration) {
        this(traderConfiguration.getTradingDataSource());
    }

    @RequestMapping("/status")
    public String status() {
        return "BDDTrader running against " + tradingDataSource;
    }
}
