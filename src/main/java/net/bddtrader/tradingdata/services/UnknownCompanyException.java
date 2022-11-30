package net.bddtrader.tradingdata.services;

public class UnknownCompanyException extends RuntimeException {
    public UnknownCompanyException(String message) {
        super(message);
    }
}
