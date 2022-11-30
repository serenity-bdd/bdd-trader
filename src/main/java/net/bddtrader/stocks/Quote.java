package net.bddtrader.stocks;

public class Quote {private String symbol;
    private String companyName;
    private String primaryExchange;
    private String calculationPrice;
    private float open;
    private float openTime;
    private float close;
    private float closeTime;
    private float high;
    private float low;
    private float latestPrice;
    private String latestSource;
    private String latestTime;
    private float latestUpdate;
    private float latestVolume;
    private float iexRealtimePrice;
    private float iexRealtimeSize;
    private float iexLastUpdated;
    private float delayedPrice;
    private float delayedPriceTime;
    private float oddLotDelayedPrice;
    private float oddLotDelayedPriceTime;
    private float extendedPrice;
    private float extendedChange;
    private float extendedChangePercent;
    private float extendedPriceTime;
    private float previousClose;
    private float previousVolume;
    private float change;
    private float changePercent;
    private float volume;
    private float iexMarketPercent;
    private float iexVolume;
    private float avgTotalVolume;
    private float iexBidPrice;
    private float iexBidSize;
    private float iexAskPrice;
    private float iexAskSize;
    private float marketCap;
    private float peRatio;
    private float week52High;
    private float week52Low;
    private float ytdChange;
    private float lastTradeTime;
    private boolean isUSMarketOpen;


    // Getter Methods

    public String getSymbol() {
        return symbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getPrimaryExchange() {
        return primaryExchange;
    }

    public String getCalculationPrice() {
        return calculationPrice;
    }

    public float getOpen() {
        return open;
    }

    public float getOpenTime() {
        return openTime;
    }

    public float getClose() {
        return close;
    }

    public float getCloseTime() {
        return closeTime;
    }

    public float getHigh() {
        return high;
    }

    public float getLow() {
        return low;
    }

    public float getLatestPrice() {
        return latestPrice;
    }

    public String getLatestSource() {
        return latestSource;
    }

    public String getLatestTime() {
        return latestTime;
    }

    public float getLatestUpdate() {
        return latestUpdate;
    }

    public float getLatestVolume() {
        return latestVolume;
    }

    public float getIexRealtimePrice() {
        return iexRealtimePrice;
    }

    public float getIexRealtimeSize() {
        return iexRealtimeSize;
    }

    public float getIexLastUpdated() {
        return iexLastUpdated;
    }

    public float getDelayedPrice() {
        return delayedPrice;
    }

    public float getDelayedPriceTime() {
        return delayedPriceTime;
    }

    public float getOddLotDelayedPrice() {
        return oddLotDelayedPrice;
    }

    public float getOddLotDelayedPriceTime() {
        return oddLotDelayedPriceTime;
    }

    public float getExtendedPrice() {
        return extendedPrice;
    }

    public float getExtendedChange() {
        return extendedChange;
    }

    public float getExtendedChangePercent() {
        return extendedChangePercent;
    }

    public float getExtendedPriceTime() {
        return extendedPriceTime;
    }

    public float getPreviousClose() {
        return previousClose;
    }

    public float getPreviousVolume() {
        return previousVolume;
    }

    public float getChange() {
        return change;
    }

    public float getChangePercent() {
        return changePercent;
    }

    public float getVolume() {
        return volume;
    }

    public float getIexMarketPercent() {
        return iexMarketPercent;
    }

    public float getIexVolume() {
        return iexVolume;
    }

    public float getAvgTotalVolume() {
        return avgTotalVolume;
    }

    public float getIexBidPrice() {
        return iexBidPrice;
    }

    public float getIexBidSize() {
        return iexBidSize;
    }

    public float getIexAskPrice() {
        return iexAskPrice;
    }

    public float getIexAskSize() {
        return iexAskSize;
    }

    public float getMarketCap() {
        return marketCap;
    }

    public float getPeRatio() {
        return peRatio;
    }

    public float getWeek52High() {
        return week52High;
    }

    public float getWeek52Low() {
        return week52Low;
    }

    public float getYtdChange() {
        return ytdChange;
    }

    public float getLastTradeTime() {
        return lastTradeTime;
    }

    public boolean getIsUSMarketOpen() {
        return isUSMarketOpen;
    }

    // Setter Methods

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setPrimaryExchange(String primaryExchange) {
        this.primaryExchange = primaryExchange;
    }

    public void setCalculationPrice(String calculationPrice) {
        this.calculationPrice = calculationPrice;
    }

    public void setOpen(float open) {
        this.open = open;
    }

    public void setOpenTime(float openTime) {
        this.openTime = openTime;
    }

    public void setClose(float close) {
        this.close = close;
    }

    public void setCloseTime(float closeTime) {
        this.closeTime = closeTime;
    }

    public void setHigh(float high) {
        this.high = high;
    }

    public void setLow(float low) {
        this.low = low;
    }

    public void setLatestPrice(float latestPrice) {
        this.latestPrice = latestPrice;
    }

    public void setLatestSource(String latestSource) {
        this.latestSource = latestSource;
    }

    public void setLatestTime(String latestTime) {
        this.latestTime = latestTime;
    }

    public void setLatestUpdate(float latestUpdate) {
        this.latestUpdate = latestUpdate;
    }

    public void setLatestVolume(float latestVolume) {
        this.latestVolume = latestVolume;
    }

    public void setIexRealtimePrice(float iexRealtimePrice) {
        this.iexRealtimePrice = iexRealtimePrice;
    }

    public void setIexRealtimeSize(float iexRealtimeSize) {
        this.iexRealtimeSize = iexRealtimeSize;
    }

    public void setIexLastUpdated(float iexLastUpdated) {
        this.iexLastUpdated = iexLastUpdated;
    }

    public void setDelayedPrice(float delayedPrice) {
        this.delayedPrice = delayedPrice;
    }

    public void setDelayedPriceTime(float delayedPriceTime) {
        this.delayedPriceTime = delayedPriceTime;
    }

    public void setOddLotDelayedPrice(float oddLotDelayedPrice) {
        this.oddLotDelayedPrice = oddLotDelayedPrice;
    }

    public void setOddLotDelayedPriceTime(float oddLotDelayedPriceTime) {
        this.oddLotDelayedPriceTime = oddLotDelayedPriceTime;
    }

    public void setExtendedPrice(float extendedPrice) {
        this.extendedPrice = extendedPrice;
    }

    public void setExtendedChange(float extendedChange) {
        this.extendedChange = extendedChange;
    }

    public void setExtendedChangePercent(float extendedChangePercent) {
        this.extendedChangePercent = extendedChangePercent;
    }

    public void setExtendedPriceTime(float extendedPriceTime) {
        this.extendedPriceTime = extendedPriceTime;
    }

    public void setPreviousClose(float previousClose) {
        this.previousClose = previousClose;
    }

    public void setPreviousVolume(float previousVolume) {
        this.previousVolume = previousVolume;
    }

    public void setChange(float change) {
        this.change = change;
    }

    public void setChangePercent(float changePercent) {
        this.changePercent = changePercent;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public void setIexMarketPercent(float iexMarketPercent) {
        this.iexMarketPercent = iexMarketPercent;
    }

    public void setIexVolume(float iexVolume) {
        this.iexVolume = iexVolume;
    }

    public void setAvgTotalVolume(float avgTotalVolume) {
        this.avgTotalVolume = avgTotalVolume;
    }

    public void setIexBidPrice(float iexBidPrice) {
        this.iexBidPrice = iexBidPrice;
    }

    public void setIexBidSize(float iexBidSize) {
        this.iexBidSize = iexBidSize;
    }

    public void setIexAskPrice(float iexAskPrice) {
        this.iexAskPrice = iexAskPrice;
    }

    public void setIexAskSize(float iexAskSize) {
        this.iexAskSize = iexAskSize;
    }

    public void setMarketCap(float marketCap) {
        this.marketCap = marketCap;
    }

    public void setPeRatio(float peRatio) {
        this.peRatio = peRatio;
    }

    public void setWeek52High(float week52High) {
        this.week52High = week52High;
    }

    public void setWeek52Low(float week52Low) {
        this.week52Low = week52Low;
    }

    public void setYtdChange(float ytdChange) {
        this.ytdChange = ytdChange;
    }

    public void setLastTradeTime(float lastTradeTime) {
        this.lastTradeTime = lastTradeTime;
    }

    public void setIsUSMarketOpen(boolean isUSMarketOpen) {
        this.isUSMarketOpen = isUSMarketOpen;
    }
}
