package net.bddtrader.tradingdata.services;


public class MissingTestDataFileException extends RuntimeException {
    public MissingTestDataFileException(String source) {
        super("Missing test data file: " + source);
    }
}
