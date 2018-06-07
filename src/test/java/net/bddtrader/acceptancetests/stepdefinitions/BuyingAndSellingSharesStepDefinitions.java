package net.bddtrader.acceptancetests.stepdefinitions;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.bddtrader.acceptancetests.actors.CastOfTraders;
import net.bddtrader.acceptancetests.questions.ThePortfolio;
import net.bddtrader.acceptancetests.tasks.PlaceOrder;
import net.bddtrader.acceptancetests.tasks.RegisterWithBDDTrader;
import net.bddtrader.clients.Client;
import net.bddtrader.portfolios.Position;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.thucydides.core.util.EnvironmentVariables;

import java.util.List;
import java.util.Map;

import static net.bddtrader.portfolios.TradeType.Buy;
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


    @When("^s?he purchases (\\d+) (.*) shares at \\$(.*) each$")
    public void purchases_shares(int amount, String securityCode, double marketPrice) {

        Actor trader = OnStage.theActorInTheSpotlight();

        Client registeredClient = trader.recall("registeredClient");

        trader.attemptsTo(
                PlaceOrder.to(Buy, amount)
                          .sharesOf(securityCode)
                          .atPriceOf(marketPrice)
                          .forClient(registeredClient)
        );
    }

    @Then("^s?he should have the following positions:$")
    public void should_have_the_following_positions(List<Position> expecetedPositions) {

        Actor trader = OnStage.theActorInTheSpotlight();

        Client registeredClient = trader.recall("registeredClient");

        List<Position> clientPositons = trader.asksFor(ThePortfolio.positionsForClient(registeredClient));

        assertThat(clientPositons)
                .usingElementComparatorOnFields("securityCode","amount")
                .containsAll(expecetedPositions);
    }

}
