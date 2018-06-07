package net.bddtrader.portfolios;

import net.bddtrader.portfolios.dsl.*;

public class TradeBuilder implements SharesOf, AtAPriceOf, CentsEach, DollarsEach, InCurrency {
    private final TradeType tradeType;
    private final Long numberOfShares;
    private String securityCode;
    private Long priceInCents;
    private Double priceInDollars;

    public TradeBuilder(TradeType tradeType, Long numberOfShares) {
        this.tradeType = tradeType;
        this.numberOfShares = numberOfShares;
    }

    @Override
    public AtAPriceOf sharesOf(String securityCode) {
        this.securityCode = securityCode;
        return this;
    }

    @Override
    public CentsEach at(Long priceInCents) {
        this.priceInCents = priceInCents;
        return this;
    }

    @Override
    public DollarsEach at(Double priceInDollars) {
        this.priceInDollars = priceInDollars;;
        return this;
    }

    @Override
    public Trade atMarketPrice() {
        return new Trade(securityCode, tradeType, numberOfShares, 0L);
    }

    public Trade centsEach() {
        return new Trade(securityCode, tradeType, numberOfShares, priceInCents);
    }

    @Override
    public Trade dollarsEach() {
        return new Trade(securityCode, tradeType, numberOfShares, (long) (priceInDollars * 100));
    }

    @Override
    public Trade dollars() {
        return new Trade(Trade.CASH_ACCOUNT, tradeType, numberOfShares * 100, 1L);
    }

    @Override
    public Trade cents() {
        return new Trade(Trade.CASH_ACCOUNT, tradeType, numberOfShares, 1L);
    }


}
