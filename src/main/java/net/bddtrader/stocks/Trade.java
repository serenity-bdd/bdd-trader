package net.bddtrader.stocks;

public class Trade {
    private float price;
    private float size;
    private float tradeId;
    private boolean isISO;
    private boolean isOddLot;
    private boolean isOutsideRegularHours;
    private boolean isSinglePriceCross;
    private boolean isTradeThroughExempt;
    private float timestamp;


    // Getter Methods

    public float getPrice() {
        return price;
    }

    public float getSize() {
        return size;
    }

    public float getTradeId() {
        return tradeId;
    }

    public boolean getIsISO() {
        return isISO;
    }

    public boolean getIsOddLot() {
        return isOddLot;
    }

    public boolean getIsOutsideRegularHours() {
        return isOutsideRegularHours;
    }

    public boolean getIsSinglePriceCross() {
        return isSinglePriceCross;
    }

    public boolean getIsTradeThroughExempt() {
        return isTradeThroughExempt;
    }

    public float getTimestamp() {
        return timestamp;
    }

    // Setter Methods

    public void setPrice(float price) {
        this.price = price;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public void setTradeId(float tradeId) {
        this.tradeId = tradeId;
    }

    public void setIsISO(boolean isISO) {
        this.isISO = isISO;
    }

    public void setIsOddLot(boolean isOddLot) {
        this.isOddLot = isOddLot;
    }

    public void setIsOutsideRegularHours(boolean isOutsideRegularHours) {
        this.isOutsideRegularHours = isOutsideRegularHours;
    }

    public void setIsSinglePriceCross(boolean isSinglePriceCross) {
        this.isSinglePriceCross = isSinglePriceCross;
    }

    public void setIsTradeThroughExempt(boolean isTradeThroughExempt) {
        this.isTradeThroughExempt = isTradeThroughExempt;
    }

    public void setTimestamp(float timestamp) {
        this.timestamp = timestamp;
    }
}
