package net.bddtrader.apitests.clients;

import net.bddtrader.clients.Client;
import net.bddtrader.clients.ClientController;
import net.bddtrader.clients.ClientDirectory;
import net.bddtrader.config.TradingDataSource;
import net.bddtrader.portfolios.*;
import net.bddtrader.tradingdata.TradingData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static net.bddtrader.config.TradingDataSource.DEV;
import static org.assertj.core.api.Assertions.assertThat;

public class WhenDifferentTypesOfClientsRegister {

    ClientDirectory clientDirectory = new ClientDirectory();
    PortfolioDirectory portfolioDirectory = new PortfolioDirectory();
    PortfolioController portfolioController = new PortfolioController(TradingDataSource.DEV, portfolioDirectory);
    ClientController controller = new ClientController(clientDirectory, portfolioController);

    @BeforeEach
    public void resetTestData() {
        TradingData.instanceFor(DEV).reset();
    }

    @ParameterizedTest
    @CsvSource(value = {"Sarah-Jane,Smith", "Bill,Oddie", "Tim,Brooke-Taylor"})
    public void aClientRegisters(String firstName, String lastName) {

        // WHEN
        Client registeredClient = controller.register(Client.withFirstName(firstName).andLastName(lastName).andEmail("sarah-jane@smith.com"));

        // THEN
        assertThat(registeredClient).isEqualToComparingFieldByField(registeredClient);
    }


}
