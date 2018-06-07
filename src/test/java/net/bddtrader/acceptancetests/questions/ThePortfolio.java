package net.bddtrader.acceptancetests.questions;

import net.bddtrader.acceptancetests.endpoints.BDDTraderEndPoints;
import net.bddtrader.clients.Client;
import net.bddtrader.portfolios.Position;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.rest.questions.RestQuestionBuilder;

import java.util.List;

public class ThePortfolio {

    public static Question<Float> cashBalanceFor(Client client) {

        return new RestQuestionBuilder<Float>().about("Cash account balance")
                .to(BDDTraderEndPoints.ClientPortfolio.path())
                .with(request -> request.pathParam("clientId", client.getId()))
                .returning(response -> response.path("cash"));

    }

    public static Question<Integer> idFor(Client client) {

        return new RestQuestionBuilder<Integer>().about("Cash account balance")
                .to(BDDTraderEndPoints.ClientPortfolio.path())
                .with(request -> request.pathParam("clientId", client.getId()))
                .returning(response -> response.path("portfolioId"));

    }

    public static Question<List<Position>> positionsForClient(Client registeredClient) {

        return new RestQuestionBuilder<List<Position>>()
                .about("positions for client " + registeredClient)
                .to(BDDTraderEndPoints.ClientPortfolioPositions.path())
                .with(request -> request.pathParam("clientId", registeredClient.getId()))
                .returning(response -> response.jsonPath().getList("", Position.class));

    }
}
