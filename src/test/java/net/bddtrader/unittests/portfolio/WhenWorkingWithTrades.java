package net.bddtrader.unittests.portfolio;

import net.bddtrader.portfolios.Trade;
import org.junit.Test;

import static net.bddtrader.portfolios.TradeType.*;
import static org.assertj.core.api.Assertions.assertThat;

public class WhenWorkingWithTrades {

    @Test
    public void purchasingActionsShouldCalculateTheTotalPriceOfTheTrade() {

        Trade trade = Trade.buy(10L).sharesOf("AAPL").at(20000L).centsEach();

        assertThat(trade.getType()).isEqualTo(Buy);
        assertThat(trade.getTotalInCents()).isEqualTo(200000);

    }

    @Test
    public void sellinggActionsShouldCalculateTheTotalPriceOfTheTrade() {

        Trade trade = Trade.sell(10L).sharesOf("AAPL").at(20000L).centsEach();

        assertThat(trade.getType()).isEqualTo(Sell);
        assertThat(trade.getTotalInCents()).isEqualTo(200000);

    }

    @Test
    public void makingACashDeposit() {

        Trade trade = Trade.deposit(1000L).dollars();

        assertThat(trade.getType()).isEqualTo(Deposit);
        assertThat(trade.getTotalInCents()).isEqualTo(100000);

    }

    @Test
    public void makingACashDepositInCents() {

        Trade trade = Trade.deposit(1000L).cents();

        assertThat(trade.getType()).isEqualTo(Deposit);
        assertThat(trade.getTotalInCents()).isEqualTo(1000);

    }

}

