package net.bddtrader.acceptancetests.endpoints;

public enum BDDTraderEndPoints {
    RegisterClient("/client"),
    ClientPortfolio("/client/{clientId}/portfolio");

    private final String relativePath;

    BDDTraderEndPoints(String relativePath) {
        this.relativePath = relativePath;
    }

    public String relativePath() {
        return relativePath;
    }
}
