package net.bddtrader.stocks;

public class TopStock {
    private final String symbol;
    private final Long volume;


    public TopStock(String symbol, Long volume) {
        this.symbol = symbol;
        this.volume = volume;
    }

    public String getSymbol() {
        return symbol;
    }

    public Long getVolume() {
        return volume;
    }
}
