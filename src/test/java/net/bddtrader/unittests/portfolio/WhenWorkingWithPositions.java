package net.bddtrader.unittests.portfolio;

import net.bddtrader.portfolios.Position;
import net.bddtrader.portfolios.Trade;
import org.junit.Test;

import static net.bddtrader.portfolios.Trade.*;
import static org.assertj.core.api.Assertions.assertThat;

public class WhenWorkingWithPositions {

    @Test
    public void applyingAnInitialTrade() {

        Trade trade = buy(10L).sharesOf("AAPL").at(500L).centsEach();
        Position position = Position.fromTrade(trade);

        assertThat(position.getSecurityCode()).isEqualTo("AAPL");
        assertThat(position.getMarketValueInDollars()).isEqualTo(5.0);
        assertThat(position.getTotalPurchasePriceInDollars()).isEqualTo(50.00);
        assertThat(position.getTotalValueInDollars()).isEqualTo(50.00);

    }

    @Test
    public void applyingABuyTrade() {

        Trade trade = buy(10L).sharesOf("AAPL").at(500L).centsEach();
        Position position = Position.fromTrade(trade);

        Position newPosition = position.apply(buy(10L).sharesOf("AAPL").at(500L).centsEach());

        assertThat(newPosition.getSecurityCode()).isEqualTo("AAPL");
        assertThat(newPosition.getTotalPurchasePriceInDollars()).isEqualTo(100.00);
        assertThat(newPosition.getTotalValueInDollars()).isEqualTo(100.00);
        assertThat(newPosition.getMarketValueInDollars()).isEqualTo(5.00);
    }


    @Test
    public void applyingASellTrade() {

        Trade trade = buy(10L).sharesOf("AAPL").at(500L).centsEach();
        Position newPosition = Position.fromTrade(trade)
                                       .apply(buy(10L).sharesOf("AAPL").at(500L).centsEach())
                                       .apply(sell(2L).sharesOf("AAPL").at(500L).centsEach());

        assertThat(newPosition.getSecurityCode()).isEqualTo("AAPL");
        assertThat(newPosition.getAmount()).isEqualTo(18);
        assertThat(newPosition.getTotalPurchasePriceInDollars()).isEqualTo(90.00);
        assertThat(newPosition.getTotalValueInDollars()).isEqualTo(90.00);
        assertThat(newPosition.getMarketValueInDollars()).isEqualTo(5.00);
    }

    @Test
    public void applyingBuyAndSellTrades() {

        Trade trade = buy(10L).sharesOf("AAPL").at(500L).centsEach();
        Position newPosition = Position.fromTrade(trade)
                .apply(buy(10L).sharesOf("AAPL").at(500L).centsEach())
                .apply(buy(20L).sharesOf("AAPL").at(500L).centsEach())
                .apply(sell(2L).sharesOf("AAPL").at(500L).centsEach());

        assertThat(newPosition.getSecurityCode()).isEqualTo("AAPL");
        assertThat(newPosition.getAmount()).isEqualTo(38);
        assertThat(newPosition.getTotalPurchasePriceInDollars()).isEqualTo(190.00);
        assertThat(newPosition.getTotalValueInDollars()).isEqualTo(190.00);
        assertThat(newPosition.getMarketValueInDollars()).isEqualTo(5.00);
    }

    @Test
    public void depositing100DollarsInCash() {

        Trade trade = deposit(100L).dollars();
        Position position = Position.fromTrade(trade);

        assertThat(position.getSecurityCode()).isEqualTo("CASH");
        assertThat(position.getMarketValueInDollars()).isEqualTo(0.01);
        assertThat(position.getAmount()).isEqualTo(10000L);
        assertThat(position.getTotalPurchasePriceInDollars()).isEqualTo(100.00);
        assertThat(position.getTotalValueInDollars()).isEqualTo(100.00);

    }

    @Test
    public void theProfitOfTheCashAccountIsAlwaysZero() {

        Trade trade = deposit(100L).dollars();
        Position position = Position.fromTrade(trade);

        assertThat(position.getProfit()).isEqualTo(0.0);
    }

}

