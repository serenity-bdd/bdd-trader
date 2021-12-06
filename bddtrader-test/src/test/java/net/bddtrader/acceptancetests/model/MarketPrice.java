package net.bddtrader.acceptancetests.model;

public class MarketPrice {
    private final String securityCode;
    private final double price;

    public MarketPrice(String securityCode, double price) {
        this.securityCode = securityCode;
        this.price = price;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public double getPrice() {
        return price;
    }
}
