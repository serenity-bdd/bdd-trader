package net.bddtrader.portfolios;

import net.bddtrader.tradingdata.PriceReader;

import java.util.HashMap;
import java.util.Map;

class Positions {

        private final Map<String, Position> positionsBySecurity = new HashMap<>();

        public void apply(Trade trade) {
            if (positionsBySecurity.containsKey(trade.getSecurityCode())) {
                positionsBySecurity.put(trade.getSecurityCode(),
                                        positionsBySecurity.get(trade.getSecurityCode()).apply(trade));
            } else {
                positionsBySecurity.put(trade.getSecurityCode(), Position.fromTrade(trade));
            }
        }

        public Map<String,Position> getPositions() {
            return new HashMap<>(positionsBySecurity);
        }

        public void updateMarketPricesUsing(PriceReader priceReader) {
            positionsBySecurity.keySet().forEach(
                    securityCode -> {
                        Double marketPrice = priceReader.getPriceFor(securityCode);
                        positionsBySecurity.put(securityCode,
                                                positionsBySecurity.get(securityCode).withMarketPriceOf(marketPrice));
                    }
            );
        }
    }