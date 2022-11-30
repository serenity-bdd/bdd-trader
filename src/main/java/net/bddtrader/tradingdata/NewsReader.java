package net.bddtrader.tradingdata;

import net.bddtrader.news.NewsItem;

import java.util.List;

public interface NewsReader {
    List<NewsItem> getNewsFor(List<String> stockids);
}
