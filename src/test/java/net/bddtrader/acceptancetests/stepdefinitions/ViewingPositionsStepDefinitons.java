package net.bddtrader.acceptancetests.stepdefinitions;

import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import net.bddtrader.acceptancetests.endpoints.BDDTraderEndPoints;
import net.bddtrader.acceptancetests.model.MarketPrice;
import net.bddtrader.acceptancetests.questions.ThePortfolio;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.rest.interactions.Get;
import net.serenitybdd.screenplay.rest.interactions.Post;

import java.util.List;
import java.util.Map;

import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.assertj.core.api.Assertions.assertThat;

public class ViewingPositionsStepDefinitons {

    @Given("the following market prices:")
    public void marketPrices(List<MarketPrice> marketPrices) {
        marketPrices.forEach(
                this::updateMarketPrice
        );
    }

    private void updateMarketPrice(MarketPrice marketPrice) {
        theActorCalled("Market Forces").attemptsTo(
                Post.to(BDDTraderEndPoints.UpdatePrice.path())
                     .with(request -> request.pathParam("securityCode", marketPrice.getSecurityCode())
                                             .header("Content-Type", "application/json")
                                             .body(marketPrice.getPrice()))
        );
        assertThat(SerenityRest.lastResponse().statusCode()).isEqualTo(200);
    }

    @And("^the overall profit should be \\$(.*)$")
    public void theOverallProfitShouldBe(double expectedProfit) throws Throwable {

        Integer portfolioId = theActorInTheSpotlight().recall("clientPortfolioId");

        theActorInTheSpotlight().attemptsTo(
                Get.resource(BDDTraderEndPoints.PortfolioProfit.path())
                   .with(request -> request.pathParam("portfolioId", portfolioId)
                                           .header("Content-Type", "application/json"))
        );

        Double actualProfit = SerenityRest.lastResponse().as(Double.class);

        assertThat(actualProfit).isEqualTo(expectedProfit);
    }
}
