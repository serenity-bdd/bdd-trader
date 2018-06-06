package net.bddtrader.apitests.stocks;

import net.bddtrader.stocks.StockController;
import net.bddtrader.tradingdata.TradingData;
import net.bddtrader.tradingdata.exceptions.IllegalPriceManiuplationException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.HttpClientErrorException;

import static net.bddtrader.config.TradingDataSource.DEV;
import static net.bddtrader.config.TradingDataSource.IEX;
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

    @Test(expected = HttpClientErrorException.class)
    public void shouldReportResourceNotFoundForUnknownStocks() {
        controller = new StockController(IEX);
        controller.priceFor("UNKNOWN-STOCK");
    }

    @Test
    public void priceForCashOnIEXShouldAlwaysBe1Cent() {
        controller = new StockController(IEX);
        assertThat(controller.priceFor("CASH")).isEqualTo(0.01);
    }

    @Test
    public void shouldFindPopularStocks() {
        controller = new StockController(DEV);
        assertThat(controller.getPopularStocks()).isNotEmpty();
    }


    @Test
    public void shouldFindPopularStocksFromIEX() {
        controller = new StockController(IEX);
        // If the markets are closed, this can return an empty list
        assertThat(controller.getPopularStocks()).isNotNull();
    }

    @Test
    public void shouldAllowPricesToBeUpdatedProgrammaticallyInDev() {

        controller = new StockController(DEV);

        controller.updatePriceFor("IBM", 200.00);

        assertThat(controller.priceFor("IBM")).isEqualTo(200.00);
    }

    @Test(expected = IllegalPriceManiuplationException.class)
    public void shouldNotAllowPricesToBeUpdatedProgrammaticallyInDev() {

        controller = new StockController(IEX);

        controller.updatePriceFor("IBM", 200.00);

        assertThat(controller.priceFor("IBM")).isEqualTo(200.00);
    }

}
