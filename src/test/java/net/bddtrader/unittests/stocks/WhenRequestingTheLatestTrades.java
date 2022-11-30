package net.bddtrader.unittests.stocks;

import net.bddtrader.stocks.StockController;
import net.bddtrader.tradingdata.TradingData;
import net.bddtrader.tradingdata.services.NoSuchCompanyException;
import net.bddtrader.tradingdata.services.UnknownCompanyException;
import org.junit.Before;
import org.junit.Test;

import static net.bddtrader.config.TradingDataSource.DEV;
import static org.assertj.core.api.Assertions.assertThat;

public class WhenRequestingTheLatestTrades {

    StockController controller;

    @Before
    public void prepareController() {
        controller = new StockController(DEV);
        TradingData.instanceFor(DEV).reset();
    }

    @Test
    public void shouldFindTheLatestPriceForAParticularStockFromStaticData() {
        assertThat(controller.bookDetailsFor ("AAPL").getQuote().getSymbol()).isEqualTo("AAPL");
        assertThat(controller.bookDetailsFor ("AAPL").getTrades()).isNotEmpty();
    }

    @Test(expected= NoSuchCompanyException.class)
    public void shouldThrowExceptionIfNoCompanyFound() {
        controller.bookDetailsFor ("Unknown").getQuote().getSymbol();
    }

}
