package net.bddtrader.portfolios;

public class Position {
    public static final Position EMPTY_CASH_POSITION = new Position("CASH", 0L, 0L, 0L);
    private final String securityCode;
    private final Long amount;
    private final Long totalPurchasePriceInCents;
    private final Long marketValueInCents;


    public Position(String securityCode, Long amount, Long totalPurchasePriceInCents, Long marketValueInCents) {
        this.securityCode = securityCode;
        this.amount = amount;
        this.totalPurchasePriceInCents = totalPurchasePriceInCents;
        this.marketValueInCents = marketValueInCents;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public Long getAmount() {
        return amount;
    }

    public double getMarketValueInDollars() {
        return ((double) marketValueInCents) / 100;
    }

    public double getTotalPurchasePriceInDollars() {
        return ((double) totalPurchasePriceInCents) / 100;
    }

    protected Long getTotalValueInCents() {
        return amount * marketValueInCents;
    }


    public double getTotalValueInDollars() {
        return ((double) getTotalValueInCents()) / 100;
    }

    public static Position fromTrade(Trade trade) {
        return new Position(trade.getSecurityCode(), trade.getAmount(), trade.getTotalInCents(), trade.getPriceInCents());
    }

    public Position apply(Trade newTrade) {
        return new Position(securityCode,
                amount + newTrade.getAmount() * newTrade.getType().multiplier(),
                totalPurchasePriceInCents + newTrade.getTotalInCents() * newTrade.getType().multiplier(),
                newTrade.getPriceInCents()
        );
    }

    public Position withMarketPriceOf(Double marketPrice) {
        return new Position(securityCode, amount, totalPurchasePriceInCents, (long) (marketPrice * 100));
    }

    public Double getProfit() {
        if (getSecurityCode().equals(Trade.CASH_ACCOUNT)) {
            return 0.0;
        }

        return ((double) (getTotalValueInCents() - totalPurchasePriceInCents)) / 100;
    }
}
