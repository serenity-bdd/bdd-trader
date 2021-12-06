package net.bddtrader.portfolios;

import net.bddtrader.tradingdata.PriceReader;

import java.util.List;
import java.util.Map;

public class PortfolioWithPositions extends Portfolio {

    private Map<String, Position> marketPositions;
    private Double profit;

    public PortfolioWithPositions(Long portfolioId, Long clientId, List<Trade> history, PriceReader priceReader) {
        super(portfolioId, clientId, history);

        marketPositions = calculatePositionsUsing(priceReader);
        profit = calculateProfitUsing(priceReader);
    }

    public Map<String, Position> getMarketPositions() {
        return marketPositions;
    }

    public Double getProfit() {
        return profit;
    }
}
