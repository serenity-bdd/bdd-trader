package net.bddtrader.status;

import io.swagger.v3.oas.annotations.Operation;
import net.bddtrader.config.TraderConfiguration;
import net.bddtrader.config.TradingDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

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

    @RequestMapping(value = "/api/status", method = GET)
    @Operation(description = "Check the status of the API")
    public String status() {
        return "BDDTrader up and running";
    }
}
