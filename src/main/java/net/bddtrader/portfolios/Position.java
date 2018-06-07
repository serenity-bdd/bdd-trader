package net.bddtrader.portfolios;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Objects;

import static net.bddtrader.portfolios.MoneyCalculations.dollarsFromCents;
import static net.bddtrader.portfolios.MoneyCalculations.roundCents;

public class Position {
    public static final Position EMPTY_CASH_POSITION = new Position(Trade.CASH_ACCOUNT, 0L, 0.0, 0.0, 0.0, 0.0);

    private final String securityCode;
    private final Long amount;
    private final double totalValueInDollars;
    private final double marketValueInDollars;
    private final double totalPurchasePriceInDollars;
    private final double profit;

    @JsonCreator
    public Position(@JsonProperty("securityCode") String securityCode,
                    @JsonProperty("amount") Long amount,
                    @JsonProperty("totalPurchasePriceInDollars") double totalPurchasePriceInDollars,
                    @JsonProperty("marketValueInDollars") double marketValueInDollars,
                    @JsonProperty("totalValueInDollars") double totalValueInDollars,
                    @JsonProperty("profit") double profit) {
        this.securityCode = securityCode;
        this.amount = amount;
        this.totalValueInDollars = totalValueInDollars;
        this.marketValueInDollars = marketValueInDollars;
        this.totalPurchasePriceInDollars = totalPurchasePriceInDollars;
        this.profit = profit;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public Long getAmount() {
        return amount;
    }

    public double getTotalValueInDollars() {
        return totalValueInDollars;
    }

    public double getMarketValueInDollars() {
        return marketValueInDollars;
    }

    public double getTotalPurchasePriceInDollars() {
        return totalPurchasePriceInDollars;
    }

    public double getProfit() {
        return profit;
    }

    public static Position fromTrade(Trade trade) {
        return new Position(trade.getSecurityCode(),
                trade.getAmount(),
                dollarsFromCents(trade.getTotalInCents()),
                dollarsFromCents(trade.getPriceInCents()),
                dollarsFromCents(trade.getTotalInCents()),
                0.0
        );
    }


    public Position withMarketPriceOf(Double marketPrice) {
        return new Position(securityCode,
                amount,
                totalPurchasePriceInDollars,
                marketPrice,
                calculatedTotalValueFor(amount, marketPrice),
                calculatedProfitFor(securityCode, amount, marketPrice, totalPurchasePriceInDollars)
        );
    }

    private static double calculatedTotalValueFor(Long amount, Double marketPrice) {
        return roundCents(marketPrice * amount);
    }

    private static double calculatedProfitFor(String securityCode, Long amount, Double marketPrice, Double totalPurchasePriceInDollars) {
        if (securityCode.equals(Trade.CASH_ACCOUNT)) { return 0.0; }
        return roundCents(calculatedTotalValueFor(amount, marketPrice) - totalPurchasePriceInDollars);
    }
    

    public Position apply(Trade newTrade) {
        long newAmount = amount + newTrade.getAmount() * newTrade.getType().multiplier();
        double newPurchasePrice = totalPurchasePriceInDollars + (((double) newTrade.getTotalInCents() * newTrade.getType().multiplier()) / 100);
        double newMarketPrice = ((double) newTrade.getPriceInCents()) / 100;

        return new Position(securityCode,
                newAmount,
                newPurchasePrice,
                newMarketPrice,
                calculatedTotalValueFor(newAmount, newMarketPrice),
                calculatedProfitFor(securityCode, newAmount, newMarketPrice, newPurchasePrice)
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return Double.compare(position.totalValueInDollars, totalValueInDollars) == 0 &&
                Double.compare(position.marketValueInDollars, marketValueInDollars) == 0 &&
                Double.compare(position.totalPurchasePriceInDollars, totalPurchasePriceInDollars) == 0 &&
                Double.compare(position.profit, profit) == 0 &&
                Objects.equal(securityCode, position.securityCode) &&
                Objects.equal(amount, position.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(securityCode, amount, totalValueInDollars, marketValueInDollars, totalPurchasePriceInDollars, profit);
    }

    @Override
    public String toString() {
        return "Position{" +
                "securityCode='" + securityCode + '\'' +
                ", amount=" + amount +
                ", totalValueInDollars=" + totalValueInDollars +
                ", marketValueInDollars=" + marketValueInDollars +
                ", totalPurchasePriceInDollars=" + totalPurchasePriceInDollars +
                ", profit=" + profit +
                '}';
    }
}
