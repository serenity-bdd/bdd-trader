package net.bddtrader.acceptancetests.stepdefinitions;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.bddtrader.acceptancetests.questions.ThePortfolio;
import net.bddtrader.acceptancetests.tasks.RegisterWithBDDTrader;
import net.bddtrader.clients.Client;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import org.springframework.http.HttpStatus;

import java.util.List;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class ClientStepDefinitions {

    private Actor tim;
    private Client client;

    @Given("^a trader with the following details:$")
    public void a_trader_with_the_following_details(List<Client> clients) {
        tim = OnStage.theActorCalled("Tim the trader");
        client = clients.get(0); // We are only interested in a single client
    }


    @When("^the trader (?:attempts to register|registers) with BDD Trader$")
    public void the_trader_attempts_to_register_with_BDD_Trader() {

        tim.attemptsTo(
                RegisterWithBDDTrader.asANewClient(client)
        );
    }

    @Then("^the registration should be rejected with the message '(.*)'$")
    public void the_registration_should_be_rejected_with_the_message(String message) {
        tim.should(
                seeThatResponse("An appropriate error message was returned",
                        response -> response.statusCode(HttpStatus.PRECONDITION_FAILED.value())
                                .body("message", equalTo(message)))
        );
    }

    @Then("^the trader should have a portfolio with \\$(.*) in cash$")
    public void should_have_portfolio_with_cash(float expectedBalance) {

        Client registeredClient = tim.recall("registeredClient");

        tim.should(
                seeThat(ThePortfolio.cashBalanceFor(registeredClient), is(equalTo(expectedBalance)))
        );
    }
}
