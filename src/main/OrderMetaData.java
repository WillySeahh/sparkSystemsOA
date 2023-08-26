package main;

import java.math.BigInteger;

public class OrderMetaData {

    public String source, symbol;
    public BigInteger timestamp;
    public double bidPrice, askPrice;

    public OrderMetaData(String source, String symbol, BigInteger timestamp, double bidPrice, double askPrice) {
        this.source = source;
        this.symbol = symbol;
        this.timestamp = timestamp;
        this.bidPrice = bidPrice;
        this.askPrice = askPrice;
    }


    public String toString(Boolean ask) {
        if (ask) {
            return String.format("Source: %s, Timestamp: %s, AskPrice: %f", source, timestamp, askPrice);
        } else {
            return String.format("Source: %s, Timestamp: %s, BidPrice: %f", source, timestamp, bidPrice);
        }
    }
}
