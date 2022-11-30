package net.bddtrader.tradingdata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Trade {
    private final String symbol;
    private final Double price;
    private final Long size;
    private final Long time;


    @JsonCreator
    public Trade(@JsonProperty("symbol") String symbol,
                 @JsonProperty("price") Double price,
                 @JsonProperty("size") Long size,
                 @JsonProperty("time") Long time) {
        this.symbol = symbol;
        this.size = size;
        this.price = price;
        this.time = time;
    }

}
