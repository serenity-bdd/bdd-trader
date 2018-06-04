package net.bddtrader.acceptancetests.screenplay.questions;

import net.serenitybdd.screenplay.Question;

public class ThePortfolio {
    public static Question<Double> cashBalance() {
        return actor -> 1000.00;
    }
}
