package net.bddtrader.portfolios;

import static net.bddtrader.portfolios.TradeDirection.Decrease;
import static net.bddtrader.portfolios.TradeDirection.Increase;

public enum TradeType {
    Deposit(Increase), // Deposit cash into the portfolio
    Withdraw(Decrease), // With cash from the portfolio
    Buy(Increase),     // Buy some shares
    Sell(Decrease);     // Sell some shares

    private final TradeDirection direction;

    TradeType(TradeDirection direction) {
        this.direction = direction;
    }

    public int multiplier() {
        return direction.multiplier;
    }

    public TradeDirection direction() {
        return direction;
    }
}
