package net.bddtrader.portfolios;

public enum TradeDirection {

    Increase(1),
    Decrease(-1);

    public final int multiplier;

    TradeDirection(int multiplier) {
        this.multiplier = multiplier;
    }
}
