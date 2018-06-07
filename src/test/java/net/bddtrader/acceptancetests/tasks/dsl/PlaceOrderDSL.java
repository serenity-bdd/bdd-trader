package net.bddtrader.acceptancetests.tasks.dsl;

import net.bddtrader.acceptancetests.tasks.PlaceOrder;
import net.bddtrader.clients.Client;

public class PlaceOrderDSL {
    public interface SharesOf {
        AtAPriceOf sharesOf(String securityCode);
    }

    public interface AtAPriceOf {
        AtAPriceOf atPriceOf(double marketPrice);
        PlaceOrder forClient(Client client);
    }
}
