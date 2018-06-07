package net.bddtrader.acceptancetests.stepdefinitions;

import cucumber.api.java.en.Then;
import net.bddtrader.acceptancetests.endpoints.BDDTraderEndPoints;
import net.bddtrader.acceptancetests.questions.ThePortfolio;
import net.bddtrader.acceptancetests.tasks.FetchTransactionHistory;
import net.bddtrader.clients.Client;
import net.bddtrader.portfolios.Trade;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.rest.interactions.Get;
import org.hamcrest.Matchers;

import java.util.List;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TransactionHistoryStepDefinitions {

    @Then("^(?:his|her) transaction history should be the following:$")
    public void his_transaction_history_should_be_the_following(List<Trade> transactionHistory) throws Exception {

        Client registeredClient = theActorInTheSpotlight().recall("registeredClient");

        theActorInTheSpotlight().attemptsTo(
                FetchTransactionHistory.forClient(registeredClient)
        );

//        List<Trade> actualTransactionHistory = ThePortfolio.historyFor(registeredClient).answeredBy(theActorInTheSpotlight());

        theActorInTheSpotlight().should(
                seeThat(ThePortfolio.history(), equalTo(transactionHistory))

        );
//        List<Trade> actualTransactionHistory = SerenityRest.lastResponse()
//                                                           .jsonPath()
//                                                           .getList("history", Trade.class);
//
//        assertThat(actualTransactionHistory).usingElementComparatorIgnoringFields("id","timestamp")
//                                            .containsExactlyElementsOf(transactionHistory);
    }

}
