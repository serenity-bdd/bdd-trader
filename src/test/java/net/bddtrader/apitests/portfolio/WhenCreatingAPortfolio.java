package net.bddtrader.apitests.portfolio;

import net.bddtrader.portfolios.InsufficientFundsException;
import net.bddtrader.portfolios.Portfolio;
import org.junit.Test;

import static net.bddtrader.portfolios.Trade.buy;
import static net.bddtrader.portfolios.Trade.sell;
import static org.assertj.core.api.Assertions.assertThat;

public class WhenCreatingAPortfolio {

    Portfolio portfolio = new Portfolio(1L,1L);

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

}
