package net.bddtrader.acceptancetests.tasks;

import net.bddtrader.acceptancetests.endpoints.BDDTraderEndPoints;
import net.bddtrader.acceptancetests.questions.ThePortfolio;
import net.bddtrader.acceptancetests.tasks.dsl.PlaceOrderDSL;
import net.bddtrader.clients.Client;
import net.bddtrader.portfolios.Trade;
import net.bddtrader.portfolios.TradeType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;
import net.serenitybdd.screenplay.rest.questions.TheResponse;
import net.serenitybdd.screenplay.rest.questions.TheResponseStatusCode;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class  PlaceOrder implements Task {

    private final TradeType buyOrSell;
    private final Long quantity;
    private String securityCode;
    private double price = 0.0;
    private final Client client;

    public PlaceOrder(TradeType type, long quantity, String securityCode, double price, Client client) {
        this.buyOrSell = type;
        this.quantity = quantity;
        this.securityCode = securityCode;
        this.price = price;
        this.client = client;
    }

    public static PlaceOrderDSL.SharesOf to(TradeType type, int quantity) {
        return new PlaceOrderBuilder(type, quantity);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {

        Integer portfolioId = actor.asksFor(ThePortfolio.idFor(client));

        actor.attemptsTo(
                Post.to(BDDTraderEndPoints.PlaceOrder.path())
                        .with(request -> request.header("Content-Type", "application/json")
                                .body(Trade.of(buyOrSell, quantity)
                                           .sharesOf(securityCode)
                                           .at(price)
                                           .dollarsEach())
                                .pathParam("portfolioId",portfolioId)
                        )
        );

        actor.should(seeThat(TheResponse.statusCode(), equalTo(200)));

    }

    public static class PlaceOrderBuilder implements PlaceOrderDSL.SharesOf, PlaceOrderDSL.AtAPriceOf {

        private final TradeType type;
        private final int quantity;
        private String securityCode;
        private double price = 0.0;

        public PlaceOrderBuilder(TradeType type, int quantity) {
            this.type = type;
            this.quantity = quantity;
        }

        @Override
        public PlaceOrderDSL.AtAPriceOf sharesOf(String securityCode) {
            this.securityCode = securityCode;
            return this;
        }

        @Override
        public PlaceOrderDSL.AtAPriceOf atPriceOf(double price) {
            this.price = price;
            return this;
        }

        @Override
        public PlaceOrder forClient(Client client) {
            return instrumented(PlaceOrder.class, type, quantity, securityCode, price, client);
        }
    }
}
