package net.bddtrader.tops;

import io.swagger.v3.oas.annotations.Operation;
import net.bddtrader.config.TraderConfiguration;
import net.bddtrader.config.TradingDataSource;
import net.bddtrader.tradingdata.TradingData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class TopController {

    private final TradingDataSource tradingDataSource;

    public TopController(TradingDataSource tradingDataSource) {
        this.tradingDataSource = tradingDataSource;
    }

    @Autowired
    public TopController(TraderConfiguration traderConfiguration) {
        this(traderConfiguration.getTradingDataSource());
    }

    @RequestMapping(value = "/api/tops/last", method = GET)
    @Operation(description = "Find the latest trades, in summary form")
    public List<Top> lastTops() {
        return TradingData.instanceFor(tradingDataSource).getTops();
    }
}
