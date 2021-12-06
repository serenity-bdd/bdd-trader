package net.bddtrader.acceptancetests.endpoints;

public enum BDDTraderEndPoints {
    RegisterClient("/client"),
    ClientPortfolio("/client/{clientId}/portfolio"),
    ClientPortfolioPositions("/client/{clientId}/portfolio/positions"),
    PlaceOrder("/portfolio/{portfolioId}/order"),
    UpdatePrice("/stock/{securityCode}/price");

    private final String path;

    BDDTraderEndPoints(String path) {
        this.path = path;
    }

    public String path() {
        return path;
    }
}
