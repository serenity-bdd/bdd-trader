package net.bddtrader.acceptancetests.questions;

import net.bddtrader.acceptancetests.endpoints.BDDTraderEndPoints;
import net.bddtrader.clients.Client;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.rest.questions.RestQuestionBuilder;

public class ThePortfolio {

    public static Question<Float> cashBalanceFor(Client client) {

        return new RestQuestionBuilder<Float>().about("Cash account balance")
                                                    .to(BDDTraderEndPoints.ClientPortfolio.relativePath())
                                                    .with(request -> request.pathParam("clientId", client.getId()))
                                                    .returning(response -> response.path("cash"));

    }
}
