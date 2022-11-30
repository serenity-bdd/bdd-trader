package net.bddtrader.unittests.status;

import net.bddtrader.config.TradingDataSource;
import net.bddtrader.status.StatusController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class WhenCheckingApplicationStatus {

    StatusController controller;

    @BeforeEach
    public void prepareNewsController() {
        controller = new StatusController(TradingDataSource.DEV);
    }

    @Test
    public void statusShouldIncludeTradeDataSource() {

        controller = new StatusController(TradingDataSource.DEV);

        assertThat(controller.status()).isEqualTo("BDDTrader up and running");
    }

}
