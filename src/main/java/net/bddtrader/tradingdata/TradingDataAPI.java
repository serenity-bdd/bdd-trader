package net.bddtrader.tradingdata;

import net.bddtrader.stocks.TradeBook;
import net.bddtrader.tops.Top;

import java.util.List;

public interface TradingDataAPI extends PriceReader, CompanyReader, PriceUpdater, NewsReader {
    void reset();

    List<Trade> getLatestTrades();

    List<Top> getTops();

    TradeBook getBookFor(String stockid);
}
