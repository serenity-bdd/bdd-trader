package net.bddtrader.apitests.status;

import net.bddtrader.config.TradingDataSource;
import net.bddtrader.status.StatusController;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static net.bddtrader.config.TradingDataSource.DEV;
import static org.assertj.core.api.Assertions.assertThat;

public class WhenCheckingApplicationStatus {

    StatusController controller;

    @Before
    public void prepareNewsController() {
        controller = new StatusController(DEV);
    }

    @Test
    public void statusShouldIncludeTradeDataSource() {

        controller = new StatusController(DEV);

        assertThat(controller.status()).isEqualTo("BDDTrader running against DEV");
    }

}
