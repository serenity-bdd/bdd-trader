package net.bddtrader.portfolios;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.bddtrader.config.TraderConfiguration;
import net.bddtrader.config.TradingDataSource;
import net.bddtrader.tradingdata.TradingData;
import net.bddtrader.tradingdata.TradingDataAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@Api("portfolio")
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
    @ApiOperation("Create a portfolio for a client")
    public Portfolio createPortfolioForClient(@PathVariable Long clientId) {
        return portfolioDirectory.addPortfolioFor(clientId);
    }

    @RequestMapping(value = "/api/client/{clientId}/portfolio", method = GET)
    @ApiOperation("View the portfolio of a client")
    public ResponseEntity<Portfolio> viewPortfolioForClient(@PathVariable Long clientId) {

        Optional<Portfolio> portfolio = portfolioDirectory.findByClientId(clientId);

        return portfolio.map(
                portfolioFound   -> new ResponseEntity<>(portfolioFound.withMarketPricesFrom(tradingDataAPI), OK))
                .orElseGet(() -> new ResponseEntity<>(NOT_FOUND));
    }


    @RequestMapping(value = "/api/portfolio/{portfolioId}", method = GET)
    @ApiOperation("View a portfolio with a given ID")
    public ResponseEntity<Portfolio>  viewPortfolio(@PathVariable Long portfolioId) {

        Optional<Portfolio> portfolio = portfolioDirectory.findById(portfolioId);

        return portfolio.map(
                portfolioFound -> new ResponseEntity<>(portfolioFound.withMarketPricesFrom(tradingDataAPI), OK))
                .orElseGet(() -> new ResponseEntity<>(NOT_FOUND));
    }

    @RequestMapping(value = "/api/portfolio/{portfolioId}/order", method = POST)
    @ApiOperation(value="Place an order for a trade in a given portfolio",
                  notes="Use the special CASH security code to deposit more money into the portfolio")
    public ResponseEntity<Portfolio> placeOrder(@PathVariable Long portfolioId,
                                                @RequestBody Trade trade) {

        Optional<Portfolio> portfolio = portfolioDirectory.findById(portfolioId);

        if (!portfolio.isPresent()) {
            return new ResponseEntity<>(NOT_FOUND);
        }

        Portfolio foundPortfolio = portfolio.get();

        try {
            foundPortfolio.placeOrderUsingPricesFrom(tradingDataAPI).forTrade(trade);
        } catch (InsufficientFundsException notEnoughDough) {
            return new ResponseEntity<>(PAYMENT_REQUIRED);
        }

        return new ResponseEntity<>(foundPortfolio, OK);
    }

    @RequestMapping(value = "/api/portfolio/{portfolioId}/history", method = GET)
    @ApiOperation("See the history of all the trades made in this portfolio")
    public ResponseEntity<List<Trade>> getHistory(@PathVariable Long portfolioId) {

        Optional<Portfolio> portfolio = portfolioDirectory.findById(portfolioId);

        return portfolio.map(
                foundPortfolio -> new ResponseEntity<>(foundPortfolio.getHistory(), OK))
                .orElseGet(() -> new ResponseEntity<>(NOT_FOUND));
    }

    @RequestMapping(value = "/api/portfolio/{portfolioId}/positions", method = GET)
    @ApiOperation("Get the current positions for a given portfolio")
    public ResponseEntity<Map<String,Position>> getPositions(@PathVariable Long portfolioId) {

        Optional<Portfolio> portfolio = portfolioDirectory.findById(portfolioId);

        return portfolio.map(
                foundPortfolio -> new ResponseEntity<>(foundPortfolio.calculatePositionsUsing(tradingDataAPI), OK))
                .orElseGet(() -> new ResponseEntity<>(NOT_FOUND));
    }

    @RequestMapping(value = "/api/portfolio/{portfolioId}/profit", method = GET)
    @ApiOperation("Get the overall profit or loss value for a given portfolio")
    public ResponseEntity<Double> getProfitAndLoss(@PathVariable Long portfolioId) {

        Optional<Portfolio> portfolio = portfolioDirectory.findById(portfolioId);

        return portfolio.map(
                foundPortfolio -> new ResponseEntity<>(foundPortfolio.calculateProfitUsing(tradingDataAPI), OK))
                .orElseGet(() -> new ResponseEntity<>(NOT_FOUND));
    }

}
