package net.bddtrader.portfolios.dsl;

import net.bddtrader.portfolios.Trade;

public interface InCurrency {
    Trade dollars();
    Trade cents();
}
