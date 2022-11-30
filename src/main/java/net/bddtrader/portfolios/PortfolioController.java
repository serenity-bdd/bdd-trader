package net.bddtrader.portfolios;

import io.swagger.v3.oas.annotations.Operation;
import net.bddtrader.config.TraderConfiguration;
import net.bddtrader.config.TradingDataSource;
import net.bddtrader.tradingdata.TradingData;
import net.bddtrader.tradingdata.TradingDataAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class PortfolioController {

    private final PortfolioDirectory portfolioDirectory;
    private final TradingDataAPI tradingDataAPI;

    public PortfolioController(TradingDataSource tradingDataSource, PortfolioDirectory portfolioDirectory) {
        this.portfolioDirectory = portfolioDirectory;
        this.tradingDataAPI = TradingData.instanceFor(tradingDataSource);
    }

    @Autowired
    public PortfolioController(TraderConfiguration traderConfiguration, PortfolioDirectory portfolioDirectory) {
        this(traderConfiguration.getTradingDataSource(), portfolioDirectory);
    }

    @RequestMapping(value = "/api/client/{clientId}/portfolio", method = POST)
    @Operation(description = "Create a portfolio for a client")
    public Portfolio createPortfolioForClient(@PathVariable Long clientId) {
        return portfolioDirectory.addPortfolioFor(clientId);
    }

    @RequestMapping(value = "/api/client/{clientId}/portfolio", method = GET)
    @Operation(description = "View the portfolio of a client")
    public Portfolio viewPortfolioForClient(@PathVariable Long clientId) {

        Portfolio portfolioFound = portfolioDirectory.findByClientId(clientId)
                .orElseThrow(() -> new PortfolioNotFoundException("No portfolio found for client id " + clientId));

        return portfolioFound.withMarketPricesFrom(tradingDataAPI);
    }

    @RequestMapping(value = "/api/client/{clientId}/portfolio/positions", method = GET)
    @Operation(description = "View the portfolio positions of a client")
    public List<Position> viewPortfolioPositionsForClient(@PathVariable Long clientId) {

        Portfolio portfolioFound = portfolioDirectory.findByClientId(clientId)
                .orElseThrow(() -> new PortfolioNotFoundException("No portfolio found for client id " + clientId));

        return getPositions(portfolioFound.getPortfolioId());
    }


    @RequestMapping(value = "/api/portfolio/{portfolioId}", method = GET)
    @Operation(description = "View a portfolio with a given ID")
    public Portfolio viewPortfolio(@PathVariable Long portfolioId) {

        return findById(portfolioId).withMarketPricesFrom(tradingDataAPI);
    }

    @RequestMapping(value = "/api/portfolio/{portfolioId}/order", method = POST)
    @Operation(summary = "Place an order for a trade in a given portfolio",
            description = "Use the special CASH security code to deposit more money into the portfolio")
    public Portfolio placeOrder(@PathVariable Long portfolioId,
                                @RequestBody Trade trade) {

        Portfolio foundPortfolio = findById(portfolioId);

        foundPortfolio.placeOrderUsingPricesFrom(tradingDataAPI).forTrade(trade);

        return foundPortfolio;
    }

    @RequestMapping(value = "/api/portfolio/{portfolioId}/history", method = GET)
    @Operation(description = "See the history of all the trades made in this portfolio")
    public List<Trade> getHistory(@PathVariable Long portfolioId) {

        return findById(portfolioId).getHistory();
    }

    @RequestMapping(value = "/api/portfolio/{portfolioId}/positions", method = GET)
    @Operation(description = "Get the current positions for a given portfolio")
    public List<Position> getPositions(@PathVariable Long portfolioId) {
        return getIndexedPositions(portfolioId).values()
                .stream()
                .sorted(comparing(Position::getSecurityCode))
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/api/portfolio/{portfolioId}/indexed-positions", method = GET)
    @Operation(description = "Get the current positions for a given portfolio as a Map")
    public Map<String, Position> getIndexedPositions(@PathVariable Long portfolioId) {
        return findById(portfolioId).calculatePositionsUsing(tradingDataAPI);
    }

    @RequestMapping(value = "/api/portfolio/{portfolioId}/profit", method = GET)
    @Operation(description = "Get the overall profit or loss value for a given portfolio")
    public Double getProfitAndLoss(@PathVariable Long portfolioId) {

        return findById(portfolioId).calculateProfitUsing(tradingDataAPI);
    }

    private Portfolio findById(@PathVariable Long portfolioId) {
        return portfolioDirectory.findById(portfolioId)
                .orElseThrow(() -> new PortfolioNotFoundException("No portfolio found for id " + portfolioId));
    }

}
