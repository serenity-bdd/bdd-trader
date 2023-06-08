package net.bddtrader.clients;

import net.bddtrader.exceptions.MissingMandatoryFieldsException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static org.springframework.util.StringUtils.isEmpty;

/**
 * An in-memory directory of registered clients.
 */
@Component
public class ClientDirectory {

    private final List<Client> registeredClients = new CopyOnWriteArrayList<>();
    private final AtomicLong clientCount = new AtomicLong(1);

    public Client registerClient(Client newClient) {

        ensureMandatoryFieldsArePresentFor(newClient);

        Client registeredClient = new Client(clientCount.getAndIncrement(),
                newClient.getFirstName(),
                newClient.getLastName(),
                newClient.getEmail());
        registeredClients.add(registeredClient);
        return registeredClient;
    }

    public void updateClient(Long clientId, Client client) {
        deleteClientById(clientId);
        Client updatedClient = new Client(clientId,
                client.getFirstName(),
                client.getLastName(),
                client.getEmail());
        registeredClients.add(updatedClient);
    }

    private void ensureMandatoryFieldsArePresentFor(Client newClient) {

        List<String> missingMandatoryFields = new ArrayList<>();
        if (isEmpty(newClient.getFirstName())) {
            missingMandatoryFields.add("firstName");
        }
        if (isEmpty(newClient.getLastName())) {
            missingMandatoryFields.add("lastName");
        }
        if (isEmpty(newClient.getEmail())) {
            missingMandatoryFields.add("email");
        }

        if (!missingMandatoryFields.isEmpty()) {
            throw new MissingMandatoryFieldsException("Missing mandatory fields for client: "
                    + missingMandatoryFields.stream().collect(Collectors.joining(", ")));
        }
    }

    public Optional<Client> findClientById(long id) {
        return registeredClients.stream()
                .filter( client -> client.getId() == id)
                .findFirst();
    }

    public void deleteClientById(long id) {
        Optional<Client> existingClient = findClientById(id);
        existingClient.ifPresent(
                registeredClients::remove
        );
    }

    public List<Client> findAll() {
        return new ArrayList(registeredClients);
    }
}
