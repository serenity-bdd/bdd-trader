package net.bddtrader.acceptancetests.model;

import java.util.Objects;

public class PositionSummary {
    private final String securityCode;
    private final Long amount;

    public PositionSummary(String securityCode, Long amount) {
        this.securityCode = securityCode;
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PositionSummary that = (PositionSummary) o;
        return Objects.equals(securityCode, that.securityCode) &&
                Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(securityCode, amount);
    }
}
