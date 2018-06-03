package net.bddtrader.apitests.portfolio;

import net.bddtrader.clients.Client;
import net.bddtrader.clients.ClientController;
import net.bddtrader.clients.ClientDirectory;
import net.bddtrader.config.TraderConfiguration;
import net.bddtrader.config.TradingDataSource;
import net.bddtrader.portfolios.*;
import net.bddtrader.tradingdata.TradingData;
import net.bddtrader.tradingdata.TradingDataAPI;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static net.bddtrader.portfolios.Trade.buy;
import static net.bddtrader.portfolios.Trade.sell;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class WhenClientsUseTheirPortfolios {

    PortfolioDirectory portfolioDirectory = new PortfolioDirectory();
    ClientDirectory clientDirectory = new ClientDirectory();
    PortfolioController portfolioController = new PortfolioController(TradingDataSource.DEV, portfolioDirectory);
    ClientController clientController = new ClientController(clientDirectory, portfolioController);

    @Test
    public void whenAClientRegistersTheyAreGivenAPortfolio() {

        Client joe = clientController.register(Client.withFirstName("Sarah-Jane").andLastName("Smith"));

        Portfolio portfolio = portfolioController.viewPortfolio(joe.getId()).getBody();

        assertThat(portfolio.getCash()).isEqualTo(1000.00);
    }

    @Test
    public void clientsCanPurchaseSharesWithTheirPortfolio() {

        Client joe = clientController.register(Client.withFirstName("Sarah-Jane").andLastName("Smith"));

        Portfolio portfolio = portfolioController.viewPortfolio(joe.getId()).getBody();

        portfolioController.placeOrder(portfolio.getPortfolioId(),
                                       buy(10L).sharesOf("AAPL").at(500L).centsEach());

        Position applPosition = portfolioController.getPositions(portfolio.getPortfolioId()).getBody().get("AAPL");

        assertThat(applPosition.getAmount()).isEqualTo(10L);
    }

    @Test
    public void clientsCanPurchaseSharesWithTheirPortfolioAtMarketPrices() {

        Client joe = clientController.register(Client.withFirstName("Sarah-Jane").andLastName("Smith"));
        Portfolio portfolio = portfolioController.viewPortfolio(joe.getId()).getBody();

        TradingData.instanceFor(TradingDataSource.DEV).updatePriceFor("AAPL", 50.00);

        portfolioController.placeOrder(portfolio.getPortfolioId(),
                buy(1L).sharesOf("AAPL").atMarketPrice());

        Position applPosition = portfolioController.getPositions(portfolio.getPortfolioId()).getBody().get("AAPL");

        assertThat(applPosition.getTotalPurchasePriceInDollars()).isEqualTo(50.00);
    }

    @Test
    public void clientsCanViewTheirPositions() {

        Client joe = clientController.register(Client.withFirstName("Sarah-Jane").andLastName("Smith"));

        Portfolio portfolio = portfolioController.viewPortfolio(joe.getId()).getBody();

        portfolioController.placeOrder(portfolio.getPortfolioId(),
                buy(10L).sharesOf("AAPL").at(500L).centsEach());

        portfolioController.placeOrder(portfolio.getPortfolioId(),
                buy(20L).sharesOf("IBM").at(500L).centsEach());

        Map<String, Position> positions = portfolioController.getPositions(portfolio.getPortfolioId()).getBody();
        Position applPosition = positions.get("AAPL");
        Position ibmPosition = positions.get("IBM");
        Position cashPosition = positions.get("CASH");

        assertThat(applPosition.getAmount()).isEqualTo(10L);
        assertThat(ibmPosition.getAmount()).isEqualTo(20L);
        assertThat(cashPosition.getAmount()).isEqualTo(85000L);
    }

    @Test
    public void clientsCanViewTheirProfitsForEachPosition() {

        Client joe = clientController.register(Client.withFirstName("Sarah-Jane").andLastName("Smith"));

        Portfolio portfolio = portfolioController.viewPortfolio(joe.getId()).getBody();

        portfolioController.placeOrder(portfolio.getPortfolioId(),
                buy(10L).sharesOf("AAPL").at(10000L).centsEach());

        Map<String, Position> positions = portfolioController.getPositions(portfolio.getPortfolioId()).getBody();
        Position applPosition = positions.get("AAPL");

        assertThat(applPosition.getProfit()).isEqualTo(902.40);
    }

    @Test
    public void clientsCanViewTheirLossesForEachPosition() {

        Client joe = clientController.register(Client.withFirstName("Sarah-Jane").andLastName("Smith"));

        Portfolio portfolio = portfolioController.viewPortfolio(joe.getId()).getBody();

        portfolioController.placeOrder(portfolio.getPortfolioId(),
                buy(1L).sharesOf("AAPL").at(20000L).centsEach());

        Map<String, Position> positions = portfolioController.getPositions(portfolio.getPortfolioId()).getBody();
        Position applPosition = positions.get("AAPL");

        assertThat(applPosition.getProfit()).isEqualTo(-9.76);
    }

    @Test
    public void clientsCanViewTheirOverallProfitsAndLosses() {

        Client joe = clientController.register(Client.withFirstName("Sarah-Jane").andLastName("Smith"));

        Portfolio portfolio = portfolioController.viewPortfolio(joe.getId()).getBody();

        portfolioController.placeOrder(portfolio.getPortfolioId(),
                buy(1L).sharesOf("AAPL").at(20000L).centsEach());

        portfolioController.placeOrder(portfolio.getPortfolioId(),
                buy(50L).sharesOf("GE").at(1110L).centsEach());

        Double profitAndLoss = portfolioController.getProfitAndLoss(portfolio.getPortfolioId()).getBody();

        assertThat(profitAndLoss).isEqualTo(150.00 - 9.76);
    }



    @Test
    public void clientsCanViewTheirTradeHistory() {

        Client joe = clientController.register(Client.withFirstName("Sarah-Jane").andLastName("Smith"));

        Portfolio portfolio = portfolioController.viewPortfolio(joe.getId()).getBody();

        portfolioController.placeOrder(portfolio.getPortfolioId(),
                buy(10L).sharesOf("AAPL").at(500L).centsEach());

        portfolioController.placeOrder(portfolio.getPortfolioId(),
                buy(20L).sharesOf("IBM").at(500L).centsEach());

        List<Trade> history = portfolioController.getHistory(portfolio.getPortfolioId()).getBody();
        assertThat(history).hasSize(5);
    }

}
