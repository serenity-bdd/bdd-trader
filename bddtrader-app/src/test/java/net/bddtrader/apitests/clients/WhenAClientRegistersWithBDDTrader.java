package net.bddtrader.apitests.clients;

import net.bddtrader.clients.Client;
import net.bddtrader.clients.ClientController;
import net.bddtrader.clients.ClientDirectory;
import net.bddtrader.config.TradingDataSource;
import net.bddtrader.exceptions.MissingMandatoryFieldsException;
import net.bddtrader.portfolios.*;
import net.bddtrader.tradingdata.TradingData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.util.List;

import static net.bddtrader.config.TradingDataSource.DEV;
import static org.assertj.core.api.Assertions.assertThat;

public class WhenAClientRegistersWithBDDTrader {

    ClientDirectory clientDirectory = new ClientDirectory();
    PortfolioDirectory portfolioDirectory = new PortfolioDirectory();
    PortfolioController portfolioController = new PortfolioController(TradingDataSource.DEV, portfolioDirectory);
    ClientController controller = new ClientController(clientDirectory, portfolioController);


    @BeforeEach
    public void resetTestData() {
        TradingData.instanceFor(DEV).reset();
    }

    @Test
    @Tag("client")
    public void aClientRegistersByProvidingANameAPasswordAndAnEmail() {

        // WHEN
        Client registeredClient = controller.register(Client.withFirstName("Sarah-Jane").andLastName("Smith").andEmail("sarah-jane@smith.com"));

        // THEN
        assertThat(registeredClient).isEqualToComparingFieldByField(registeredClient);
    }

    @Test
    @Tag("client")
    public void firstNameIsMandatory() {
        Assertions.assertThrows(MissingMandatoryFieldsException.class, () -> {
            controller.register(controller.register(Client.withFirstName("").andLastName("Smith").andEmail("sarah-jane@smith.com")));
        });
    }

    @Test
    @Tag("client")
    public void lastNameIsMandatory() {
        Assertions.assertThrows(MissingMandatoryFieldsException.class, () -> {
            controller.register(controller.register(Client.withFirstName("Sarah-Jane").andLastName("").andEmail("sarah-jane@smith.com")));
        });
    }

    @Test
    @Tag("client")
    public void emailIsMandatory() {
        Assertions.assertThrows(MissingMandatoryFieldsException.class, () -> {
            controller.register(Client.withFirstName("Sarah-Jane").andLastName("Smith").andEmail(""));
        });
    }

    @Test
    @Tag("client")
    public void registeredClientsAreStoredInTheClientDirectory() {

        // WHEN
        Client registeredClient = controller.register(Client.withFirstName("Sarah-Jane").andLastName("Smith").andEmail("sarah-jane@smith.com"));

        // THEN
        assertThat(clientDirectory.findClientById(1))
                .isPresent()
                .contains(registeredClient);
    }

    @Test
    @Tag("client")
    public void registeredClientsCanBeRetrievedById() {

        // GIVEN
        Client sarahJane = controller.register(Client.withFirstName("Sarah-Jane").andLastName("Smith").andEmail("sarah-jane@smith.com"));

        // WHEN
        Client foundClient = controller.findClientById(1L).getBody();

        // THEN
        assertThat(foundClient).isEqualToComparingFieldByField(sarahJane);
    }

    @Test
    @Tag("client")
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

    @Test
    public void shouldfailIfNoPortfolioCanBeFound() {
        // GIVEN
        controller.register(Client.withFirstName("Sarah-Jane").andLastName("Smith").andEmail("sarah-jane@smith.com"));

        // WHEN
        Assertions.assertThrows(PortfolioNotFoundException.class, () -> portfolioController.viewPortfolioForClient(-1L));
    }

    @Test
    public void shouldfailIfNoClientForAPortfolioCanBeFound() {
        // GIVEN
        Client sarahJane = controller.register(Client.withFirstName("Sarah-Jane").andLastName("Smith").andEmail("sarah-jane@smith.com"));

        // WHEN
        Assertions.assertThrows(PortfolioNotFoundException.class, () -> portfolioController.viewPortfolio(-1L));
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
        HttpStatusCode status = controller.findClientById(100L).getStatusCode();

        // THEN
        assertThat(status).isEqualTo(HttpStatus.NOT_FOUND);
    }

}
