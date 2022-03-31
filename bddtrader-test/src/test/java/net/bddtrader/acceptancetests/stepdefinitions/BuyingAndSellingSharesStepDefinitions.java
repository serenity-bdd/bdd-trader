package net.bddtrader.acceptancetests.stepdefinitions;

import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.bddtrader.acceptancetests.model.PositionSummary;
import net.bddtrader.acceptancetests.questions.ThePortfolio;
import net.bddtrader.acceptancetests.tasks.PlaceOrder;
import net.bddtrader.acceptancetests.tasks.RegisterWithBDDTrader;
import net.bddtrader.clients.Client;
import net.bddtrader.portfolios.Position;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;

import java.util.List;
import java.util.Map;
import java.util.Optional;


import static net.bddtrader.portfolios.TradeType.Buy;
import static net.bddtrader.portfolios.TradeType.Sell;
import static org.assertj.core.api.Assertions.assertThat;

public class BuyingAndSellingSharesStepDefinitions {


    @Given("{word} {word} is a registered trader")
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

    @DataTableType
    public Position positionFrom(Map<String, String> values) {

        return new Position(
                values.get("securityCode"),
                Long.parseLong(Optional.ofNullable(values.get("amount")).orElse("0")),
                values.get("totalPurchasePriceInDollars") != null ? Double.parseDouble(values.get("totalPurchasePriceInDollars")) : null,
                values.get("marketValueInDollars") != null ? Double.parseDouble(values.get("marketValueInDollars")) : null,
                values.get("totalValueInDollars") != null ? Double.parseDouble(values.get("totalValueInDollars")) : null,
                values.get("profit") != null ? Double.parseDouble(values.get("profit")) : null
        );
    }

    @DataTableType
    public PositionSummary positionSummaryFrom(Map<String, String> values) {

        return new PositionSummary(
                values.get("securityCode"),
                Long.parseLong(values.get("amount"))
        );
    }



    @Then("{actor} should have the following position details:")
    public void should_have_the_following_position_details(Actor trader, List<Position> expectedPositions) {

        Client registeredClient = trader.recall("registeredClient");

        List<Position> clientPositons = trader.asksFor(ThePortfolio.positionsForClient(registeredClient));

        assertThat(clientPositons)
                .usingElementComparatorOnFields("securityCode","amount","totalValueInDollars","profit")
                .containsAll(expectedPositions);
    }

    @Then("{actor} should have the following positions:")
    public void should_have_the_following_positions(Actor trader, List<Position> expectedPositions) {

        Client registeredClient = trader.recall("registeredClient");

        List<Position> clientPositons = trader.asksFor(ThePortfolio.positionsForClient(registeredClient));

        assertThat(clientPositons)
                .usingElementComparatorOnFields("securityCode","amount")
                .containsAll(expectedPositions);
    }



}
