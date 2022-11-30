package net.bddtrader.tradingdata;

import net.bddtrader.config.TradingDataSource;
import net.bddtrader.tradingdata.services.StaticAPI;

import java.util.HashMap;
import java.util.Map;

/**
 * Return trading data either from a remote web service, or from static local data for testing.
 * The source of that trading data is defined by the data.source system property defined in the
 * application.conf file.
 * This value can be:
 *   * DEV - Test data
 */
public class TradingData {

    private static Map<TradingDataSource, TradingDataAPI> TRADING_DATA_SOURCE_API = new HashMap<>();
    static {
        TRADING_DATA_SOURCE_API.put(TradingDataSource.DEV, new StaticAPI());
    }

    private final static TradingDataAPI DEFAULT_DATA_SOURCE = new StaticAPI();

    public static TradingDataAPI instanceFor(TradingDataSource dataSource) {
        return TRADING_DATA_SOURCE_API.getOrDefault(dataSource, DEFAULT_DATA_SOURCE);
    }
}
