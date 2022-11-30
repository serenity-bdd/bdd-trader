package net.bddtrader.portfolios;

import net.bddtrader.tradingdata.PriceReader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import static net.bddtrader.portfolios.Trade.deposit;

/**
 * A Portfolio records the financial position of a client, as well as the history of their trades.
 */
public class Portfolio {

    private static Long INITIAL_DEPOSIT_IN_DOLLARS = 1000L;

    private final Long portfolioId;
    private final Long clientId;
    private final List<Trade> history;

    public Portfolio(Long portfolioId, Long clientId) {
        this.portfolioId = portfolioId;
        this.clientId = clientId;
        this.history = new CopyOnWriteArrayList<>();
        placeOrder(deposit(INITIAL_DEPOSIT_IN_DOLLARS).dollars());
    }

    protected Portfolio(Long portfolioId, Long clientId, List<Trade> history) {
        this.portfolioId = portfolioId;
        this.clientId = clientId;
        this.history = new CopyOnWriteArrayList<>(history);
    }

    public Long getPortfolioId() {
        return portfolioId;
    }

    public Long getClientId() {
        return clientId;
    }

    public double getCash() {
        return getCashPosition().orElse(Position.EMPTY_CASH_POSITION).getTotalValueInDollars();
    }

    private Long getCashInCents() {
        return (long) (getCashPosition().orElse(Position.EMPTY_CASH_POSITION).getTotalValueInDollars() * 100);
    }

    public void placeOrder(Trade trade) {

        ensureSufficientFundsAreAvailableFor(trade);

        trade.cashTransation().ifPresent( history::add );

        history.add(trade);
    }

    public OrderPlacement placeOrderUsingPricesFrom(PriceReader priceReader) {
        return new OrderPlacement(priceReader);
    }

    public Portfolio withMarketPricesFrom(PriceReader priceReader) {
        return new PortfolioWithPositions(portfolioId, clientId, history, priceReader);
    }

    public class OrderPlacement {
        private PriceReader priceReader;

        OrderPlacement(PriceReader priceReader) {
            this.priceReader = priceReader;
        }

        public void forTrade(Trade trade) {
            if (shouldFindMarketPriceFor(trade)) {
                trade = trade.atPrice(priceReader.getPriceFor(trade.getSecurityCode()));
            }

            placeOrder(trade);
        }
    }

    private boolean shouldFindMarketPriceFor(Trade trade) {
        return trade.getPriceInCents() == 0;
    }

    private void ensureSufficientFundsAreAvailableFor(Trade trade) {

        if (!hasSufficientFundsFor(trade)){
            throw new InsufficientFundsException("Insufficient funds: " + getCash() + " for purchase of " + trade.getTotalInCents() / 100);
        }
    }

    public boolean hasSufficientFundsFor(Trade trade) {
        return (trade.getType() == TradeType.Deposit) || (trade.getType() == TradeType.Sell) || ((getCashInCents() >= trade.getTotalInCents()));

    }

    public Map<String, Position> calculatePositionsUsing(PriceReader priceReader) {

        Positions positions = getPositions();

        positions.updateMarketPricesUsing(priceReader);

        return positions.getPositions();
    }

    public Double calculateProfitUsing(PriceReader priceReader) {

        Positions positions = getPositions();

        positions.updateMarketPricesUsing(priceReader);

        return calculateProfitOn(positions);
    }

    private Optional<Position> getCashPosition() {
        return Optional.ofNullable(getPositions().getPositions().get(Trade.CASH_ACCOUNT));
    }

    public List<Trade> getHistory() {
        return new ArrayList<>(history);
    }


    private Positions getPositions() {
        Positions positions = new Positions();

        for (Trade trade : history) {
            positions.apply(trade);
        }
        return positions;
    }

    private Double calculateProfitOn(Positions positions) {
        return positions.getPositions().values().stream()
                .filter(position -> (!position.getSecurityCode().equals(Trade.CASH_ACCOUNT)))
                .mapToDouble(Position::getProfit)
                .sum();
    }
}
