package net.bddtrader.acceptancetests.stepdefinitions;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.bddtrader.acceptancetests.questions.ThePortfolio;
import net.bddtrader.acceptancetests.tasks.PlaceOrder;
import net.bddtrader.acceptancetests.tasks.RegisterWithBDDTrader;
import net.bddtrader.clients.Client;
import net.bddtrader.portfolios.Position;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;

import java.util.List;

import static net.bddtrader.portfolios.TradeType.Buy;
import static net.bddtrader.portfolios.TradeType.Sell;
import static org.assertj.core.api.Assertions.assertThat;

public class BuyingAndSellingSharesStepDefinitions {


    @Given("(\\w+) (\\w+) is a registered trader$")
    public void a_registered_trader(String firstName, String lastName) {

        Actor trader = OnStage.theActorCalled(firstName);

        trader.attemptsTo(
                RegisterWithBDDTrader.asANewClient(Client.withFirstName(firstName)
                                                         .andLastName(lastName)
                                                         .andEmail(firstName + "@" + lastName + ".com"))
        );
    }


    @When("^(.*) (?:purchases|has purchased) (\\d+) (.*) shares at \\$(.*) each$")
    public void purchases_shares(String traderName, int amount, String securityCode, double marketPrice) {

        Actor trader = OnStage.theActorCalled(traderName);

        Client registeredClient = trader.recall("registeredClient");

        trader.attemptsTo(
                PlaceOrder.to(Buy, amount)
                          .sharesOf(securityCode)
                          .atPriceOf(marketPrice)
                          .forClient(registeredClient)
        );
    }

    @When("^(.*) sells (\\d+) (.*) shares for \\$(.*) each$")
    public void sells_shares(String traderName, int amount, String securityCode, double marketPrice) {

        Actor trader = OnStage.theActorCalled(traderName);

        Client registeredClient = trader.recall("registeredClient");

        trader.attemptsTo(
                PlaceOrder.to(Sell, amount)
                        .sharesOf(securityCode)
                        .atPriceOf(marketPrice)
                        .forClient(registeredClient)
        );
    }

    @Then("^(.*) should have the following positions:$")
    public void should_have_the_following_positions(String traderName, DataTable expectedPositionTable) {

        String[] relevantFields = relevantFieldsInTable(expectedPositionTable);
        List<Position> expectedPositions = expectedPositionTable.asList(Position.class);
        Actor trader = OnStage.theActorCalled(traderName);

        Client registeredClient = trader.recall("registeredClient");

        List<Position> clientPositons = trader.asksFor(ThePortfolio.positionsForClient(registeredClient));

        assertThat(clientPositons)
                .usingElementComparatorOnFields(relevantFields)
                .containsAll(expectedPositions);
    }

    private String[] relevantFieldsInTable(DataTable expectedPositionTable) {
        return expectedPositionTable.topCells().toArray(new String[]{});
    }

}
