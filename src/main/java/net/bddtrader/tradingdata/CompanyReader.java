package net.bddtrader.tradingdata;

import net.bddtrader.stocks.Company;

public interface CompanyReader {
    Company getCompanyFor(String stockid);
}
