package net.bddtrader.portfolios;

import net.bddtrader.tradingdata.PriceReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import static net.bddtrader.portfolios.Trade.deposit;
import static net.bddtrader.portfolios.TradeType.Buy;

/**
 * A Portfolio records the financial position of a client, as well as the history of their trades.
 */
public class Portfolio {

    private static Long INITIAL_DEPOSIT_IN_DOLLARS = 1000L;

    private final Long portfolioId;
    private final Long clientId;
    private final List<Trade> history = new CopyOnWriteArrayList<>();

    public Portfolio(Long portfolioId, Long clientId) {
        this.portfolioId = portfolioId;
        this.clientId = clientId;
        placeOrder(deposit(INITIAL_DEPOSIT_IN_DOLLARS).dollars());
    }

    public Long getPortfolioId() {
        return portfolioId;
    }

    public Long getClientId() {
        return clientId;
    }

    public double getCash() {
        return getCashPosition().getTotalValueInDollars();
    }

    public Long getCashInCents() {
        return getCashPosition().getTotalValueInCents();
    }

    public void placeOrder(Trade trade) {
        ensureSufficientFundsAreAvailableFor(trade);

        trade.cashTransation().ifPresent( history::add );

        history.add(trade);
    }

    private void ensureSufficientFundsAreAvailableFor(Trade trade) {

        if (trade.getType() != TradeType.Buy) { return; }

        if (!hasSufficientFundsFor(trade)){
            throw new InsufficientFundsException("Insufficient funds: " + getCash() + " for purchase of " + trade.getTotalInCents() / 100);
        }
    }

    public boolean hasSufficientFundsFor(Trade trade) {
        if (trade.getType() == TradeType.Deposit) { return true; }

        return ((trade.getType() == Buy) && (getCashInCents() >= trade.getTotalInCents()));
    }

    public Map<String, Position> calculatePositionsUsing(PriceReader priceReader) {

        Positions positions = getCumulatedPositions();

        positions.updateMarketPricesUsing(priceReader);

        return positions.getPositions();
    }

    public Double calculateProfitUsing(PriceReader priceReader) {

        Positions positions = getCumulatedPositions();

        positions.updateMarketPricesUsing(priceReader);

        return positions.getPositions().values().stream()
                .filter(position -> (!position.getSecurityCode().equals(Trade.CASH_ACCOUNT)))
                .mapToDouble(Position::getProfit)
                .sum();
    }

    public Position getCashPosition() {
        return getCumulatedPositions().getPositions().get(Trade.CASH_ACCOUNT);
    }

    public List<Trade> getHistory() {
        return new ArrayList<>(history);
    }


    private Positions getCumulatedPositions() {
        Positions positions = new Positions();

        for (Trade trade : history) {
            positions.apply(trade);
        }
        return positions;
    }
}
