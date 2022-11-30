package net.bddtrader.portfolios;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableMap;
import net.bddtrader.portfolios.dsl.InCurrency;
import net.bddtrader.portfolios.dsl.SharesOf;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import static net.bddtrader.portfolios.TradeType.*;

public class Trade {

    public static final String CASH_ACCOUNT = "CASH";

    private final Long id;
    /**
     * The security code (e.g. AAPL), or CASH for the cash account
     */
    private final String securityCode;
    /**
     * Deposit, Buy or Sell
     */
    private LocalDateTime timestamp;
    private final TradeType type;
    private final Long amount;
    private final Long priceInCents;
    private final Long totalInCents;

    private static final Map<TradeType, TradeType> OPPOSITE_TRADETYPE = ImmutableMap.of(
            Buy, Sell,
            Sell, Buy,
            Deposit, Withdraw,
            Withdraw, Deposit
    );


    private static final AtomicLong ID_COUNTER = new AtomicLong(1);

    @JsonCreator
    protected Trade(@JsonProperty("securityCode") String securityCode,
                    @JsonProperty("type") TradeType type,
                    @JsonProperty("amount") Long amount,
                    @JsonProperty("priceInCents") Long priceInCents) {
        this.id = ID_COUNTER.getAndIncrement();
        this.timestamp = LocalDateTime.now();
        this.securityCode = securityCode;
        this.type = type;
        this.amount = amount;
        this.priceInCents = priceInCents;
        this.totalInCents = amount * priceInCents;
    }

    private Trade(Long id, String securityCode, LocalDateTime timestamp, TradeType type, Long amount, Long priceInCents, Long totalInCents) {
        this.id = id;
        this.securityCode = securityCode;
        this.timestamp = timestamp;
        this.type = type;
        this.amount = amount;
        this.priceInCents = priceInCents;
        this.totalInCents = totalInCents;
    }

    public Long getId() {
        return id;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public TradeType getType() {
        return type;
    }

    public Long getAmount() {
        return amount;
    }

    public Long getPriceInCents() {
        return priceInCents;
    }

    public Long getTotalInCents() {
        return totalInCents;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public static SharesOf of(TradeType type, Long numberOfShares) {
       return new TradeBuilder(type, numberOfShares);
    }

    public static SharesOf buy(Long numberOfShares) {
        return new TradeBuilder(Buy, numberOfShares);
    }

    public static SharesOf sell(Long numberOfShares) {
        return new TradeBuilder(Sell, numberOfShares);

    }

    public static InCurrency deposit(Long amountInCents) {
        return new TradeBuilder(TradeType.Deposit, amountInCents);
    }

    /**
     * All trades except CASH deposits have an impact on the CASH account.
     */
    public Optional<Trade> cashTransation() {
        if (securityCode.equals(CASH_ACCOUNT)) {
            return Optional.empty();
        }

        return Optional.of(new Trade(CASH_ACCOUNT, OPPOSITE_TRADETYPE.get(type), getTotalInCents(), 1L));
    }

    public Trade atPrice(Double marketPrice) {
        return new Trade(id, securityCode, timestamp, type, amount, (long) (marketPrice * 100), (long) (marketPrice  * 100 * amount));
    }

    @Override
    public String toString() {
        return "Trade{" +
                "securityCode='" + securityCode + '\'' +
                ", type=" + type +
                ", amount=" + amount +
                ", priceInCents=" + priceInCents +
                ", totalInCents=" + totalInCents +
                '}';
    }
}
