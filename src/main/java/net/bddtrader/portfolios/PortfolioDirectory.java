package net.bddtrader.portfolios;

import net.bddtrader.clients.Client;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

/**
 * An in-memory directory of registered clients.
 */
@Component
public class PortfolioDirectory {

    private final List<Portfolio> portfolios = new CopyOnWriteArrayList<>();
    private final AtomicLong portfolioCount = new AtomicLong(1);

    public Portfolio addPortfolioFor(Long clientId) {
        Portfolio portfolio = new Portfolio(portfolioCount.getAndIncrement(), clientId);
        portfolios.add(portfolio);
        return portfolio;
    }

    public Optional<Portfolio> findByClientId(Long clientId) {
        return portfolios.stream()
                .filter( portfolio -> portfolio.getClientId().equals(clientId))
                .findFirst();
    }

    public Optional<Portfolio> findById(Long portfolioId) {
        return portfolios.stream()
                .filter( portfolio -> portfolio.getPortfolioId().equals(portfolioId))
                .findFirst();
    }

}
