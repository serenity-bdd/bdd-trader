package net.bddtrader.news;

import net.bddtrader.stocks.StockController;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

import static net.bddtrader.config.TradingDataSource.DEV;
import static net.bddtrader.config.TradingDataSource.IEX;
import static org.assertj.core.api.Assertions.assertThat;

public class WhenRequestingThePrice {

    StockController controller;

    @Before
    public void prepareNewsController() {
        controller = new StockController(IEX);
    }

    @Test
    public void shouldFindTheLatestPriceForAParticularStockFromStaticData() {

        controller = new StockController(DEV);

        assertThat(controller.priceFor("AAPL")).isEqualTo(100.00);
    }

    @Test
    public void shouldFindTheLatestNewsAboutAParticularStock() {

        assertThat(controller.priceFor("AAPL")).isGreaterThan(0.00);
    }

    @Test(expected = HttpClientErrorException.class)
    public void shouldReportResourceNotFoundForUnknownStocks() {
        controller.priceFor("UNKNOWN-STOCK");
    }

}
