package net.bddtrader.apitests.portfolio;

import net.bddtrader.config.TradingDataSource;
import net.bddtrader.portfolios.InsufficientFundsException;
import net.bddtrader.portfolios.Portfolio;
import net.bddtrader.portfolios.PortfolioWithPositions;
import net.bddtrader.tradingdata.PriceReader;
import net.bddtrader.tradingdata.TradingData;
import org.junit.Before;
import org.junit.Test;

import static net.bddtrader.config.TradingDataSource.DEV;
import static net.bddtrader.portfolios.Trade.buy;
import static net.bddtrader.portfolios.Trade.deposit;
import static net.bddtrader.portfolios.Trade.sell;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WhenCreatingAPortfolio {

    Portfolio portfolio = new Portfolio(1L,1L);

    @Before
    public void resetTestData() {
        TradingData.instanceFor(DEV).reset();
    }

    @Test
    public void aNewPortfolioStartsWith1000DollarsOnACashAccount() {

        assertThat(portfolio.getCash()).isEqualTo(1000.00);
    }

    @Test
    public void purchasingSharesDrawsOnTheCashAccount() {

        portfolio.placeOrder(
                buy(50L).sharesOf("AAPL").at(100L).centsEach()
        );

        assertThat(portfolio.getCash()).isEqualTo(950.00);
    }

    @Test
    public void sellingSharesDepositsOnTheCashAccount() {

        portfolio.placeOrder(buy(200L).sharesOf("IBM").at(100L).centsEach());
        portfolio.placeOrder(sell(50L).sharesOf("IBM").at(100L).centsEach());

        assertThat(portfolio.getCash()).isEqualTo(850.00);
    }


    @Test
    public void portfolioShoudShowProfitsOrLossesBasedOnCurrentMarketPrices() {

        PriceReader priceReader = mock(PriceReader.class);

        portfolio.placeOrder(buy(10L).sharesOf("IBM").at(10000L).centsEach());

        when(priceReader.getPriceFor("IBM")).thenReturn(150.00);
        assertThat(portfolio.calculateProfitUsing(priceReader)).isEqualTo(500.00);
    }

    @Test(expected = InsufficientFundsException.class)
    public void buyerMustHaveEnoughCashToMakeAPurchase() {
        portfolio.placeOrder(buy(20000L).sharesOf("IBM").at(100L).centsEach());
    }

    @Test
    public void failedTransactionsShouldNotAffectBalance() {

        try {
            portfolio.placeOrder(buy(20000L).sharesOf("IBM").at(100L).centsEach());

        } catch (InsufficientFundsException expectedException) {
            assertThat(portfolio.getCash()).isEqualTo(1000.00);
        }
    }

    @Test
    public void portfolioWithPositionsShoudShowCashAccountValues() {

        PriceReader priceReader = TradingData.instanceFor(TradingDataSource.DEV);

        portfolio.placeOrder(deposit(1000L).dollars());

        PortfolioWithPositions portfolioWithPositions = (PortfolioWithPositions) portfolio.withMarketPricesFrom(priceReader);

        assertThat(portfolioWithPositions.getMarketPositions().get("CASH").getAmount()).isEqualTo(200000);
        assertThat(portfolioWithPositions.getMarketPositions().get("CASH").getTotalValueInDollars()).isEqualTo(2000.00);
        assertThat(portfolioWithPositions.getMarketPositions().get("CASH").getMarketValueInDollars()).isEqualTo(0.01);
    }


}
