package net.bddtrader.acceptancetests.tasks;

import net.bddtrader.acceptancetests.questions.ThePortfolio;
import net.bddtrader.clients.Client;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;

import static net.bddtrader.acceptancetests.endpoints.BDDTraderEndPoints.RegisterClient;

public class RegisterWithBDDTrader {

    public static Performable asANewClient(Client client) {

        return Task.where("{0} registers a client " + client,
                actor -> {
                    actor.attemptsTo(
                            Post.to(RegisterClient.path())
                                    .with(request -> {
                                        return request.header("Content-Type", "application/json")
                                                .body(client);
                                    })
                    );

                    if (SerenityRest.lastResponse().statusCode() == 200) {
                        Client newClient = SerenityRest.lastResponse().as(Client.class);
                        actor.remember("registeredClient", newClient);
                        actor.remember("clientPortfolioId", ThePortfolio.idFor(newClient));
                    }
                }
        );
    }
}
