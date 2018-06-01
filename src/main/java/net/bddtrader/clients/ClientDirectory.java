package net.bddtrader.clients;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * An in-memory directory of registered clients.
 */
@Component
public class ClientDirectory {

    private final List<Client> registeredClients = new ArrayList<>();
    private final AtomicLong clientCount = new AtomicLong(1);

    public Client registerClient(Client newClient) {
        Client registeredClient = new Client(clientCount.getAndIncrement(), newClient.getFirstName(), newClient.getLastName());
        registeredClients.add(registeredClient);
        return registeredClient;
    }

    public Optional<Client> findClientById(long id) {
        return registeredClients.stream()
                .filter( client -> client.getId() == id)
                .findFirst();
    }

    public List<Client> findAll() {
        return new ArrayList(registeredClients);
    }
}
