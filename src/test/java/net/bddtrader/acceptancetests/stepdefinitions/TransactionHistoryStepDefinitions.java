package net.bddtrader.acceptancetests.stepdefinitions;

import cucumber.api.java.en.Then;
import net.bddtrader.acceptancetests.questions.ThePortfolio;
import net.bddtrader.acceptancetests.tasks.FetchTransactionHistory;
import net.bddtrader.clients.Client;
import net.bddtrader.portfolios.Trade;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.Comparator;
import java.util.List;

import static net.bddtrader.acceptancetests.matchers.TradeMatchers.matchesTradesIn;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.assertj.core.api.Assertions.assertThat;

public class TransactionHistoryStepDefinitions {

    @Then("^(?:his|her) transaction history should be the following:$")
    public void his_transaction_history_should_be_the_following(List<Trade> transactionHistory) throws Exception {

        Client registeredClient = theActorInTheSpotlight().recall("registeredClient");

        theActorInTheSpotlight().attemptsTo(
                FetchTransactionHistory.forClient(registeredClient)
        );

        theActorInTheSpotlight().should(
                seeThat(ThePortfolio.history(), matchesTradesIn(transactionHistory))
        );
    }
}