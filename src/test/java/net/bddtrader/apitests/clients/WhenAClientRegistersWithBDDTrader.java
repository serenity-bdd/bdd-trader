package net.bddtrader.apitests.clients;

import net.bddtrader.clients.Client;
import net.bddtrader.clients.ClientController;
import net.bddtrader.clients.ClientDirectory;
import net.bddtrader.portfolios.PortfolioController;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class WhenAClientRegistersWithBDDTrader {

    PortfolioController portfolioController = mock(PortfolioController.class);
    ClientDirectory clientDirectory = new ClientDirectory();
    ClientController controller = new ClientController(clientDirectory, portfolioController);


    @Test
    public void aClientRegistersByProvidingANameAndAPassword() {

        // WHEN
        Client registeredClient = controller.register(new Client("Sarah-Jane","Smith"));

        // THEN
        assertThat(registeredClient).isEqualToComparingFieldByField(new Client(1L,"Sarah-Jane","Smith"));
    }

    @Test
    public void registeredClientsAreStoredInTheClientDirectory() {

        // WHEN
        Client registeredClient = controller.register(new Client("Sarah-Jane","Smith"));

        // THEN
        assertThat(clientDirectory.findClientById(1))
                .isPresent()
                .contains(registeredClient);
    }

    @Test
    public void registeredClientsCanBeRetrievedById() {

        // GIVEN
        controller.register(new Client("Sarah-Jane","Smith"));

        // WHEN
        Client foundClient = controller.findClientById(1L).getBody();

        // THEN
        assertThat(foundClient).isEqualTo(new Client(1L,"Sarah-Jane","Smith"));
    }

    @Test
    public void registeredClientsCanBeListed() {

        // GIVEN
        controller.register(new Client("Sarah-Jane","Smith"));
        controller.register(new Client("Joe","Smith"));

        // WHEN
        List<Client> foundClients = controller.findAll();

        // THEN
        assertThat(foundClients).hasSize(2);
    }

    @Test
    public void returns404WhenNoMatchingClientIsFound() {

        // GIVEN
        controller.register(new Client("Sarah-Jane","Smith"));

        // WHEN
        HttpStatus status = controller.findClientById(100L).getStatusCode();

        // THEN
        assertThat(status).isEqualTo(HttpStatus.NOT_FOUND);
    }

}
