package net.bddtrader.clients;

import net.bddtrader.config.TraderConfiguration;
import net.bddtrader.config.TradingDataSource;
import net.bddtrader.tradingdata.TradingData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@RestController
public class ClientController {

    private final ClientDirectory clientDirectory;


    @Autowired
    public ClientController(ClientDirectory clientDirectory) {
        this.clientDirectory = clientDirectory;
    }

    @RequestMapping(value = "/client", method = RequestMethod.POST)
    public Client register(@RequestBody Client newClient) {
        return clientDirectory.registerClient(newClient);
    }

    @RequestMapping(value = "/client/{clientId}")
    public ResponseEntity<Client> findClientById(@PathVariable Long clientId) {

        Optional<Client> client = clientDirectory.findClientById(clientId);

        return client.map(
                clientFound   -> new ResponseEntity<>(clientFound, OK))
                .orElseGet(() -> new ResponseEntity<>(NOT_FOUND));
    }

    @RequestMapping(value = "/clients")
    public List<Client> findAll() {
        return clientDirectory.findAll();
    }
}
