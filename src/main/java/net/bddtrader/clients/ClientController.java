package net.bddtrader.clients;

import io.swagger.v3.oas.annotations.Operation;
import net.bddtrader.portfolios.PortfolioController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Clients register with the application by providing their first and last name.
 * When clients register, they are given a portfolio with $1000.
 */
@RestController
public class ClientController {

    private final ClientDirectory clientDirectory;
    private final PortfolioController portfolioController;

    @Autowired
    public ClientController(ClientDirectory clientDirectory,
                            PortfolioController portfolioController) {

        this.clientDirectory = clientDirectory;
        this.portfolioController = portfolioController;
    }

    @RequestMapping(value = "/api/client", method = POST)
    @Operation(description = "Register a new client")
    public Client register(@RequestBody Client newClient) {
        Client client = clientDirectory.registerClient(newClient);

        portfolioController.createPortfolioForClient(client.getId());

        return client;
    }

    @RequestMapping(value = "/api/client/{clientId}", method = GET)
    @Operation(description = "Get the details of a given client")
    public ResponseEntity<Client> findClientById(@PathVariable Long clientId) {

        Optional<Client> client = clientDirectory.findClientById(clientId);

        return client.map(
                        clientFound -> new ResponseEntity<>(clientFound, OK))
                .orElseGet(() -> new ResponseEntity<>(NOT_FOUND));
    }

    @RequestMapping(value = "/api/client/{clientId}", method = PUT)
    @Operation(description = "Update the details of a given client")
    public ResponseEntity<Client> updateClientById(@PathVariable Long clientId, @RequestBody Client newClient) {

        Optional<Client> client = clientDirectory.findClientById(clientId);

        if (client.isPresent()) {
            clientDirectory.updateClient(clientId, newClient);
            return new ResponseEntity<>(newClient, OK);
        } else {
            return new ResponseEntity<>(NOT_FOUND);
        }
    }

    @RequestMapping(value = "/api/client/{clientId}", method = DELETE)
    @Operation(description = "Delete a client")
    public ResponseEntity<Client> deleteClientById(@PathVariable Long clientId) {

        clientDirectory.deleteClientById(clientId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/api/clients", method = GET)
    @Operation(description = "Get all the currently registered clients")
    public List<Client> findAll() {
        return clientDirectory.findAll();
    }
}
