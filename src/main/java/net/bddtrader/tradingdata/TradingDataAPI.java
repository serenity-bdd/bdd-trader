package net.bddtrader.tradingdata;

public interface TradingDataAPI extends PriceReader, PriceUpdater, NewsReader {
    void reset();
}
