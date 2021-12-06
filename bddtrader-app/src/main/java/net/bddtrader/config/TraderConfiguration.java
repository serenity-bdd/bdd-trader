package net.bddtrader.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.springframework.stereotype.Component;

@Component
public class TraderConfiguration {
    private final Config config = ConfigFactory.load();

    public TradingDataSource getTradingDataSource() {
        return config.getEnum(TradingDataSource.class, "data.source");
    }
}
