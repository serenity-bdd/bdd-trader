package net.bddtrader.unittests.stocks;

import net.bddtrader.stocks.StockController;
import net.bddtrader.tradingdata.TradingData;
import org.junit.Before;
import org.junit.Test;

import static net.bddtrader.config.TradingDataSource.DEV;
import static org.assertj.core.api.Assertions.assertThat;

public class WhenRequestingThePrice {

    StockController controller;

    @Before
    public void prepareNewsController() {
        controller = new StockController(DEV);
        TradingData.instanceFor(DEV).reset();
    }

    @Test
    public void shouldFindTheLatestPriceForAParticularStockFromStaticData() {

        controller = new StockController(DEV);

        assertThat(controller.priceFor("AAPL")).isEqualTo(190.24);
    }

    @Test
    public void shouldFindTheLatestNewsAboutAParticularStock() {

        assertThat(controller.priceFor("AAPL")).isGreaterThan(0.00);
    }

    @Test
    public void shouldFindPopularStocks() {
        controller = new StockController(DEV);
        assertThat(controller.getPopularStocks()).isNotEmpty();
    }

    @Test
    public void shouldAllowPricesToBeUpdatedProgrammaticallyInDev() {

        controller = new StockController(DEV);

        controller.updatePriceFor("IBM", 200.00);

        assertThat(controller.priceFor("IBM")).isEqualTo(200.00);
    }

}
