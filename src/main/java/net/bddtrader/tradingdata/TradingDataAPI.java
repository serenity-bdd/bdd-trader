package net.bddtrader.tradingdata;

import net.bddtrader.news.NewsItem;

import java.util.List;

public interface TradingDataAPI {
    List<NewsItem> getNewsFor(String stockid);
}
