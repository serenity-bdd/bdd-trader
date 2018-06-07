package net.bddtrader.acceptancetests.matchers;

import net.bddtrader.portfolios.Trade;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.Comparator;
import java.util.List;

public class TradeMatchers {
    public static Matcher<List<Trade>> matchesTradesIn(List<Trade> expectedTrades) {
        return new TypeSafeMatcher<List<Trade>>() {
            @Override
            protected boolean matchesSafely(List<Trade> actualTrades) {

                if (actualTrades.size() != expectedTrades.size()) {
                    return false;
                }

                return expectedTrades.stream().allMatch(
                        expectedTrade -> actualTrades.stream().anyMatch(
                                actualTrade -> isEquivalent(actualTrade, expectedTrade)
                        )
                );
            }

            @Override
            public void describeTo(Description description) {
                description.appendText(expectedTrades.toString());
            }
        };
    }

    private static boolean isEquivalent(Trade a, Trade b) {
        return Comparator.comparing(Trade::getAmount)
                .thenComparing(Trade::getSecurityCode)
                .thenComparing(Trade::getType)
                .thenComparing(Trade::getTotalInCents)
                .thenComparing(Trade::getPriceInCents)
                .compare(a, b) == 0;
    }
}
