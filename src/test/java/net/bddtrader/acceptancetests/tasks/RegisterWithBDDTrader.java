package net.bddtrader.acceptancetests.tasks;

import net.bddtrader.acceptancetests.questions.ThePortfolio;
import net.bddtrader.clients.Client;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;
import net.thucydides.core.annotations.Step;

import static net.bddtrader.acceptancetests.endpoints.BDDTraderEndPoints.RegisterClient;
import static net.serenitybdd.screenplay.Tasks.instrumented;

public class RegisterWithBDDTrader implements Task {

    private final Client client;

    public RegisterWithBDDTrader(Client client) {
        this.client = client;
    }

    public static Performable asANewClient(Client client) {
        return instrumented(RegisterWithBDDTrader.class, client);
    }

    @Override
    @Step("{0} registers a client #client")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Post.to(RegisterClient.path())
                        .with(request -> request.header("Content-Type", "application/json")
                                .body(client))
        );

        if (SerenityRest.lastResponse().statusCode() == 200) {
            Client client = SerenityRest.lastResponse().as(Client.class);

            actor.remember("registeredClient", client);
            actor.remember("clientPortfolioId", ThePortfolio.idFor(client));
        }
    }
}
