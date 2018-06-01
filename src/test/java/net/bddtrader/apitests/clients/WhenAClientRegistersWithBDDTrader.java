package net.bddtrader.apitests.clients;

import net.bddtrader.clients.Client;
import net.bddtrader.clients.ClientController;
import net.bddtrader.clients.ClientDirectory;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class WhenAClientRegistersWithBDDTrader {

    ClientDirectory clientDirectory = new ClientDirectory();
    ClientController controller = new ClientController(clientDirectory);

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
        Client registeredClient = controller.register(new Client("Sarah-Jane","Smith"));

        // WHEN
        Client foundClient = controller.findClientById(1L).getBody();

        // THEN
        assertThat(foundClient).isEqualTo(new Client(1L,"Sarah-Jane","Smith"));
    }

    @Test
    public void registeredClientsCanBeListed() {

        // GIVEN
        Client registeredClient1 = controller.register(new Client("Sarah-Jane","Smith"));
        Client registeredClient2 = controller.register(new Client("Joe","Smith"));

        // WHEN
        List<Client> foundClients = controller.findAll();

        // THEN
        assertThat(foundClients).hasSize(2);
    }

    @Test
    public void returns404WhenNoMatchingClientIsFound() {

        // GIVEN
        Client registeredClient = controller.register(new Client("Sarah-Jane","Smith"));

        // WHEN
        HttpStatus status = controller.findClientById(100L).getStatusCode();

        // THEN
        assertThat(status).isEqualTo(HttpStatus.NOT_FOUND);
    }

}
