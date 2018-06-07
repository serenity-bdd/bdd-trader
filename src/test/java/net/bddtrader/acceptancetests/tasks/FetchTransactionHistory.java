package net.bddtrader.acceptancetests.tasks;

import net.bddtrader.acceptancetests.endpoints.BDDTraderEndPoints;
import net.bddtrader.clients.Client;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Get;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static org.assertj.core.api.Assertions.assertThat;

public class FetchTransactionHistory implements Task {

    private final Long clientId;

    public FetchTransactionHistory(Long clientId) {
        this.clientId = clientId;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {

        actor.attemptsTo(
                Get.resource(BDDTraderEndPoints.ClientPortfolio.path())
                        .with(request -> request.pathParam("clientId", clientId))
        );

        assertThat(SerenityRest.lastResponse().statusCode()).isEqualTo(200);
    }

    public static FetchTransactionHistory forClient(Client client) {
        return instrumented(FetchTransactionHistory.class, client.getId());
    }
}
