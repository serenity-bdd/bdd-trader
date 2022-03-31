package net.bddtrader.portfolios;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MoneyCalculations {

    public static double dollarsFromCents(long valueInCents) {
        BigDecimal dollars = new BigDecimal(valueInCents).divide(new BigDecimal(100));
        return dollars.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public static double roundCents(double valueInDollars) {
        BigDecimal dollars = new BigDecimal(valueInDollars);
        return dollars.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
