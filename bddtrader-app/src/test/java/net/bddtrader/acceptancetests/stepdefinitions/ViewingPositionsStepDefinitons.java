package net.bddtrader.acceptancetests.stepdefinitions;

import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import net.bddtrader.acceptancetests.endpoints.BDDTraderEndPoints;
import net.bddtrader.acceptancetests.model.MarketPrice;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.rest.interactions.Post;

import java.util.List;
import java.util.Map;

import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;
import static org.assertj.core.api.Assertions.assertThat;

public class ViewingPositionsStepDefinitons {

    @DataTableType
    public MarketPrice marketPrice(Map<String, String> values) {
        return new MarketPrice(
                values.get("securityCode"),
                Double.parseDouble(values.get("price")
                )
        );
    }

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
}
