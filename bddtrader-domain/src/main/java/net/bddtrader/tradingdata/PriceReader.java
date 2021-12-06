package net.bddtrader.tradingdata;

import java.util.List;

public interface PriceReader {
    Double getPriceFor(String stockid);
    List<String> getPopularStocks();
}
