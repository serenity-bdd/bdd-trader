package net.bddtrader.stocks;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TopStock {
    private final String symbol;
    private final Long volume;


    @JsonCreator
    public TopStock(@JsonProperty("symbol") String symbol, @JsonProperty("volume") Long volume) {
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
