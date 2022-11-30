package net.bddtrader.unittests.news;

import net.bddtrader.stocks.Company;
import net.bddtrader.stocks.StockController;
import net.bddtrader.tradingdata.services.NoSuchCompanyException;
import net.bddtrader.tradingdata.services.UnknownCompanyException;
import org.junit.Before;
import org.junit.Test;

import static net.bddtrader.config.TradingDataSource.DEV;
import static org.assertj.core.api.Assertions.assertThat;

public class WhenReadingCompanyInfo {

    StockController controller;

    @Before
    public void prepareController() {
        controller = new StockController(DEV);
    }

    @Test
    public void shouldFindTheLatestNewsAboutAParticularStockFromStaticData() {

        Company apple = controller.companyDetailsFor("AAPL");

        assertThat(apple.getCompanyName()).isEqualTo("Apple, Inc.");
    }

    @Test
    public void stockCodesCanBeUpperOrLowerCase() {

        Company apple = controller.companyDetailsFor("aapl");

        assertThat(apple.getCompanyName()).isEqualTo("Apple, Inc.");
    }

    @Test(expected = NoSuchCompanyException.class)
    public void unknownCompanyShouldResultInAMeaningfulException() {
        controller.companyDetailsFor("unknown");
    }

}
