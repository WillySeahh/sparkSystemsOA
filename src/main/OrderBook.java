package main;

import javafx.util.Pair;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    public String queryBestBidAsk() {
         // There are no delete order, so if symbol has orderbook, there must be at least an order. Will not throw NPE here
        Double lowestAsk = this.asks.ask.firstEntry().getKey();
        OrderMetaData lowestAskMetaData = this.asks.ask.get(lowestAsk);

        Double highestBid = this.bids.bid.lastEntry().getKey();
        OrderMetaData highestBidMetaData = this.bids.bid.get(highestBid);

        return printToStringFunc(lowestAskMetaData, highestBidMetaData);
    }

    public String queryBestBidAsk(BigInteger currTime, BigInteger age) {
        // There are no delete order, so if symbol has orderbook, there must be at least an order. Will not throw NPE here
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
        // There are no delete order, so if symbol has orderbook, there must be at least an order. Will not throw NPE here
        Double lowestAsk = this.asks.ask.firstEntry().getKey();
        OrderMetaData lowestAskMetaData = this.asks.ask.get(lowestAsk);

        Double highestBid = this.bids.bid.lastEntry().getKey();
        OrderMetaData highestBidMetaData = this.bids.bid.lastEntry().getValue();

        return new Pair(lowestAskMetaData, highestBidMetaData);
    }

    public Pair<OrderMetaData, OrderMetaData> getBidAskSpread(BigInteger currTime, BigInteger age) {
        // There are no delete order, so if symbol has orderbook, there must be at least an order. Will not throw NPE here

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


    public String printEntireOrderBook() {
        StringBuilder result = new StringBuilder();

        result.append("------------------------------------\n");

        for (Double key : asks.ask.descendingKeySet()) {
            result.append(asks.ask.get(key).toString(true));
            result.append('\n');
        }

        result.append("\n");
        result.append("-----------BID ASK SPREAD---------------\n");
        result.append("\n");

        for (Double key : bids.bid.descendingKeySet()) {
            result.append(bids.bid.get(key).toString(false));
            result.append('\n');
        }

        return result.toString();
    }

    public String printEntireOrderBook(BigInteger age) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYYYMMddHHmmssSSS");
        LocalDateTime now = LocalDateTime.now();
        BigInteger currTime = new BigInteger(dtf.format(now));

        StringBuilder result = new StringBuilder();

        result.append("------------------------------------\n");

        for (Double key : asks.ask.descendingKeySet()) {
            if ((currTime.subtract(this.asks.ask.get(key).timestamp)).abs().compareTo(age) <= 0) {
                //fits this time criteria
                result.append(asks.ask.get(key).toString(true));
                result.append('\n');
            }
        }

        result.append("\n");
        result.append("-----------BID ASK SPREAD---------------\n");
        result.append("\n");

        for (Double key : bids.bid.descendingKeySet()) {
            if ((currTime.subtract(this.bids.bid.get(key).timestamp)).abs().compareTo(age) <= 0) {
                //fits this time criteria
                result.append(bids.bid.get(key).toString(false));
                result.append('\n');
            }
        }

        return result.toString();
    }


}
