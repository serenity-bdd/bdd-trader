package net.bddtrader.unittests.clients;

import net.bddtrader.clients.Client;
import net.bddtrader.clients.ClientController;
import net.bddtrader.clients.ClientDirectory;
import net.bddtrader.config.TradingDataSource;
import net.bddtrader.exceptions.MissingMandatoryFieldsException;
import net.bddtrader.portfolios.*;
import net.bddtrader.tradingdata.TradingData;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import java.util.List;

import static net.bddtrader.config.TradingDataSource.DEV;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class WhenAClientRegistersWithBDDTrader {

    ClientDirectory clientDirectory = new ClientDirectory();
    PortfolioDirectory portfolioDirectory = new PortfolioDirectory();
    PortfolioController portfolioController = new PortfolioController(TradingDataSource.DEV, portfolioDirectory);
    ClientController controller = new ClientController(clientDirectory, portfolioController);


    @Before
    public void resetTestData() {
        TradingData.instanceFor(DEV).reset();
    }

    @Test
    public void aClientRegistersByProvidingANameAPasswordAndAnEmail() {

        // WHEN
        Client registeredClient = controller.register(Client.withFirstName("Sarah-Jane").andLastName("Smith").andEmail("sarah-jane@smith.com"));

        // THEN
        assertThat(registeredClient).isEqualToComparingFieldByField(registeredClient);
    }

    @Test(expected = MissingMandatoryFieldsException.class)
    public void firstNameIsMandatory() {
        controller.register(controller.register(Client.withFirstName("").andLastName("Smith").andEmail("sarah-jane@smith.com")));
    }

    @Test(expected = MissingMandatoryFieldsException.class)
    public void lastNameIsMandatory() {
        controller.register(controller.register(Client.withFirstName("Sarah-Jane").andLastName("").andEmail("sarah-jane@smith.com")));
    }

    @Test(expected = MissingMandatoryFieldsException.class)
    public void emailIsMandatory() {
        controller.register(Client.withFirstName("Sarah-Jane").andLastName("Smith").andEmail(""));
    }

    @Test
    public void registeredClientsAreStoredInTheClientDirectory() {

        // WHEN
        Client registeredClient = controller.register(Client.withFirstName("Sarah-Jane").andLastName("Smith").andEmail("sarah-jane@smith.com"));

        // THEN
        assertThat(clientDirectory.findClientById(1))
                .isPresent()
                .contains(registeredClient);
    }

    @Test
    public void registeredClientsCanBeRetrievedById() {

        // GIVEN
        Client sarahJane = controller.register(Client.withFirstName("Sarah-Jane").andLastName("Smith").andEmail("sarah-jane@smith.com"));

        // WHEN
        Client foundClient = controller.findClientById(1L).getBody();

        // THEN
        assertThat(foundClient).isEqualToComparingFieldByField(sarahJane);
    }

    @Test
    public void registeredClientsAreGivenAPortfolio() {

        // GIVEN
        Client sarahJane = controller.register(Client.withFirstName("Sarah-Jane").andLastName("Smith").andEmail("sarah-jane@smith.com"));

        // WHEN
        Portfolio clientPortfolio = portfolioController.viewPortfolioForClient(sarahJane.getId());

        // THEN
        assertThat(clientPortfolio.getCash()).isEqualTo(1000.00);
    }

    @Test
    public void registeredClientsCanViewTheirPortfolioPositions() {

        // GIVEN
        Client sarahJane = controller.register(Client.withFirstName("Sarah-Jane").andLastName("Smith").andEmail("sarah-jane@smith.com"));

        // WHEN
        List<Position> positions = portfolioController.viewPortfolioPositionsForClient(sarahJane.getId());

        // THEN
        assertThat(positions).hasSize(1)
                             .contains(Position.fromTrade(Trade.buy(100000L).sharesOf("CASH").at(1L).centsEach()));
    }

    @Test(expected = PortfolioNotFoundException.class)
    public void shouldfailIfNoPortfolioCanBeFound() {
        // GIVEN
        Client sarahJane = controller.register(Client.withFirstName("Sarah-Jane").andLastName("Smith").andEmail("sarah-jane@smith.com"));

        // WHEN
        portfolioController.viewPortfolioForClient(-1L);
    }

    @Test(expected = PortfolioNotFoundException.class)
    public void shouldfailIfNoClientForAPortfolioCanBeFound() {
        // GIVEN
        Client sarahJane = controller.register(Client.withFirstName("Sarah-Jane").andLastName("Smith").andEmail("sarah-jane@smith.com"));

        // WHEN
        Portfolio clientPortfolio = portfolioController.viewPortfolio(-1L);
    }


    @Test
    public void registeredClientsCanBeListed() {

        // GIVEN
        controller.register(Client.withFirstName("Sarah-Jane").andLastName("Smith").andEmail("sarah-jane@smith.com"));
        controller.register(Client.withFirstName("Joe").andLastName("Smith").andEmail("joe@smith.com"));

        // WHEN
        List<Client> foundClients = controller.findAll();

        // THEN
        assertThat(foundClients).hasSize(2);
    }

    @Test
    public void returns404WhenNoMatchingClientIsFound() {

        // GIVEN
        controller.register(Client.withFirstName("Sarah-Jane").andLastName("Smith").andEmail("sarah-jane@smith.com"));

        // WHEN
        HttpStatus status = controller.findClientById(100L).getStatusCode();

        // THEN
        assertThat(status).isEqualTo(HttpStatus.NOT_FOUND);
    }

}
