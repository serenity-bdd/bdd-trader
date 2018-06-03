package net.bddtrader.portfolios.dsl;

import net.bddtrader.portfolios.Trade;

public interface AtAPriceOf {
    CentsEach at(Long priceInCents);
    Trade atMarketPrice();
}
