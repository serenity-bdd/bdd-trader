package net.bddtrader.unittests.tops;

import net.bddtrader.tops.TopController;
import net.bddtrader.tradingdata.TradingData;
import org.junit.Before;
import org.junit.Test;

import static net.bddtrader.config.TradingDataSource.DEV;
import static org.assertj.core.api.Assertions.assertThat;

public class WhenRequestingTheTops {

    TopController controller;

    @Before
    public void prepareNewsController() {
        controller = new TopController(DEV);
        TradingData.instanceFor(DEV).reset();
    }

    @Test
    public void shouldFindTheLatestPriceForAParticularStockFromStaticData() {
        assertThat(controller.lastTops()).isNotEmpty();
    }

}
