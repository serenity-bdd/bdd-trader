package net.bddtrader.acceptancetests.stepdefinitions;

import cucumber.api.java.en.Then;
import net.bddtrader.acceptancetests.endpoints.BDDTraderEndPoints;
import net.bddtrader.clients.Client;
import net.bddtrader.portfolios.Trade;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.rest.interactions.Get;

import java.util.List;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.assertj.core.api.Assertions.assertThat;

public class TransactionHistoryStepDefinitions {

    @Then("^(?:his|her) transaction history should be the following:$")
    public void his_transaction_history_should_be_the_following(List<Trade> transactionHistory) throws Exception {

        Client registeredClient = theActorInTheSpotlight().recall("registeredClient");

        theActorInTheSpotlight().attemptsTo(
                Get.resource(BDDTraderEndPoints.ClientPortfolio.path())
                   .with(request -> request.pathParam("clientId", registeredClient.getId()))
        );

        assertThat(SerenityRest.lastResponse().statusCode()).isEqualTo(200);

        List<Trade> actualTransactionHistory = SerenityRest.lastResponse()
                                                           .jsonPath()
                                                           .getList("history", Trade.class);

        assertThat(actualTransactionHistory).usingElementComparatorIgnoringFields("id","timestamp")
                                            .containsExactlyElementsOf(transactionHistory);
    }

}
