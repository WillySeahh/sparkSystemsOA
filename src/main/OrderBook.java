package main;

import javafx.util.Pair;

import java.math.BigInteger;
import java.util.Map;

public class OrderBook {

    public Asks asks;
    public Bids bids;
    public String symbol;

    public OrderBook(String symbol) {
        this.symbol = symbol;
        asks = new Asks();
        bids = new Bids();
    }

    public String printBidAskSpread() {

        Double lowestAsk = this.asks.ask.firstEntry().getKey();
        OrderMetaData lowestAskMetaData = this.asks.ask.get(lowestAsk);

        Double highestBid = this.bids.bid.lastEntry().getKey();
        OrderMetaData highestBidMetaData = this.bids.bid.lastEntry().getValue();

        return printToStringFunc(lowestAskMetaData, highestBidMetaData);
    }

    public String printBidAskSpread(BigInteger currTime, BigInteger age) {

        Double lowestAsk = null;
        OrderMetaData lowestAskMetaData = null;

        for (Double key : asks.ask.keySet()) {
            //iterating from the lowest ask price to the highest
            //can stop iterating as long we meet the first entry that fulfils the time
            if ((currTime.subtract(this.asks.ask.get(key).timestamp)).abs().compareTo(age) <= 0) {
                //if the difference in currTime and order timestamp is less than or equal to age, it is valid
                lowestAsk = key;
                lowestAskMetaData = this.asks.ask.get(key);
                break;
            }
        }

        Double highestBid = null;
        OrderMetaData highestBidMetaData = null;

        for (Double key : bids.bid.descendingKeySet()) {
            //iterate from highest bid to lowest
            if ((currTime.subtract(this.bids.bid.get(key).timestamp)).abs().compareTo(age) <= 0) {
                highestBid = key;
                highestBidMetaData = this.bids.bid.get(key);
                break;
            }
        }


        return printToStringFunc(lowestAskMetaData, highestBidMetaData);
    }

    public Pair<OrderMetaData, OrderMetaData> getBidAskSpread() {
        Double lowestAsk = this.asks.ask.firstEntry().getKey();
        OrderMetaData lowestAskMetaData = this.asks.ask.get(lowestAsk);

        Double highestBid = this.bids.bid.lastEntry().getKey();
        OrderMetaData highestBidMetaData = this.bids.bid.lastEntry().getValue();

        return new Pair(lowestAskMetaData, highestBidMetaData);
    }

    public Pair<OrderMetaData, OrderMetaData> getBidAskSpread(BigInteger currTime, BigInteger age) {
        Double lowestAsk = null;
        OrderMetaData lowestAskMetaData = null;

        for (Double key : asks.ask.keySet()) {
            //iterating from the lowest ask price to the highest
            //can stop iterating as long we meet the first entry that fulfils the time
            if ((currTime.subtract(this.asks.ask.get(key).timestamp)).abs().compareTo(age) <= 0) {
                //if the difference in currTime and order timestamp is less than or equal to age, it is valid
                lowestAsk = key;
                lowestAskMetaData = this.asks.ask.get(key);
                break;
            }
        }

        Double highestBid = null;
        OrderMetaData highestBidMetaData = null;

        for (Double key : bids.bid.descendingKeySet()) {
            //iterate from highest bid to lowest
            if ((currTime.subtract(this.bids.bid.get(key).timestamp)).abs().compareTo(age) <= 0) {
                highestBid = key;
                highestBidMetaData = this.bids.bid.get(key);
                break;
            }
        }

        return new Pair(lowestAskMetaData, highestBidMetaData);
    }



        public String printBidAskSpreadWithinTimeFrame(BigInteger currTime, BigInteger age) {
        if (age.compareTo(BigInteger.ZERO) == 0) {
            //no specific time frame, get best bid and ask price since beginning
            return printBidAskSpread();
        } else {
            return printBidAskSpread(currTime, age);
        }
    }

    public String printToStringFunc(OrderMetaData ask, OrderMetaData bid) {
        String result = "Bid Ask Spread for symbol: " + this.symbol + '\n';

        result += '\n';
        if (ask != null) {
            result += String.format("Lowest ASK = " +
                    "Source: %s, " +
                    "Timestamp: %s, " +
                    "Ask Price: %f ", ask.source, ask.timestamp, ask.askPrice);
        } else {
            result += "No ASK found within timeframe ";
        }

        result += "\n----------------------------------------- \n";

        if (bid != null) {
            result += String.format("Highest BID = " +
                    "Source: %s, " +
                    "Timestamp: %s, " +
                    "Bid Price: %f ", bid.source, bid.timestamp, bid.bidPrice);
        } else {
            result += "No BID found within timeframe ";
        }
        result += '\n';

        return result;
    }
}
