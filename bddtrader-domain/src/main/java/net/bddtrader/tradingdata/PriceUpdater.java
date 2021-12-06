package net.bddtrader.tradingdata;

public interface PriceUpdater {
    void updatePriceFor(String stockid, Double currentPrice);
}
