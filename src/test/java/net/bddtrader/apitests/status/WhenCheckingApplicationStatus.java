package net.bddtrader.apitests.status;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import net.bddtrader.config.TradingDataSource;
import net.bddtrader.status.StatusController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static net.bddtrader.config.TradingDataSource.IEX;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class WhenCheckingApplicationStatus {

    StatusController controller;

    @Before
    public void prepareNewsController() {
        controller = new StatusController(IEX);
    }

    @Test
    @Parameters({"DEV, BDDTrader running against DEV",
                 "IEX, BDDTrader running against IEX" })
    public void statusShouldIncludeTradeDataSource(TradingDataSource dataSource, String statusMessage) {

        controller = new StatusController(dataSource);

        assertThat(controller.status()).isEqualTo(statusMessage);
    }

}
