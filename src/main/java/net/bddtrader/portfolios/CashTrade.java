package net.bddtrader.portfolios;

public class CashTrade extends Trade{

    protected CashTrade(TradeType type, Long amountInCents) {
        super(CASH_ACCOUNT, type, amountInCents, 1L);
    }
}
