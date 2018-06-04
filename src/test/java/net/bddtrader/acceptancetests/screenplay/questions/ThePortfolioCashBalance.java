package net.bddtrader.acceptancetests.screenplay.questions;

import net.bddtrader.acceptancetests.endpoints.BDDTraderEndPoints;
import net.bddtrader.clients.Client;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.annotations.Subject;
import net.serenitybdd.screenplay.rest.interactions.Get;

import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;

@Subject("the portfolio cash balance")
public class ThePortfolioCashBalance implements Question<Float> {

    private final Long clientId;

    public ThePortfolioCashBalance(Long clientId) {
        this.clientId = clientId;
    }

    public static ThePortfolioCashBalance forClient(Client client) {
        return new ThePortfolioCashBalance(client.getId());
    }

    @Override
    public Float answeredBy(Actor actor) {

        actor.attemptsTo(
                Get.resource(BDDTraderEndPoints.ClientPortfolio.relativePath())
                   .with(request -> request.pathParam("clientId", clientId))
        );

        actor.should(
                seeThatResponse( response -> response.statusCode(200))
        );

        return (Float) SerenityRest.lastResponse().path("cash");
    }
}
