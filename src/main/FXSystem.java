package main;

import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class FXSystem {

    public HashMap<String, OrderBook> symbolToOrderBook = new HashMap<>();

    /**
     * Query the best bid and best ask and print them out.
     * It prints only the lowest ask and highest bid.
     * @param symbol
     * @param age
     * @return
     */
    public String queryBestBidAsk(String symbol, BigInteger age) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYYYMMddHHmmssSSS");
        LocalDateTime now = LocalDateTime.now();
        BigInteger currTime = new BigInteger(dtf.format(now));

        if (symbolToOrderBook.containsKey(symbol)) {
            return symbolToOrderBook.get(symbol).queryBestBidAsk(currTime, age);
        } else {
            return "Symbol: " + symbol + " has no ASKS or BIDS associated with it.";
        }
    }

    /**
     * Query the best bid and best ask and print them out.
     * It prints only the lowest ask and highest bid.
     * @param symbol
     * No age, so it will search through the entire orderbook
     * @return
     */
    public String queryBestBidAsk(String symbol) {

        if (symbolToOrderBook.containsKey(symbol)) {
            return symbolToOrderBook.get(symbol).queryBestBidAsk();
        } else {
            return "Symbol: " + symbol + " has no ASKS or BIDS associated with it.";
        }
    }


    public Pair<OrderMetaData, OrderMetaData> getBidAskSpread(String symbol, BigInteger age) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYYYMMddHHmmssSSS");
        LocalDateTime now = LocalDateTime.now();
        BigInteger currTime = new BigInteger(dtf.format(now));

        if (symbolToOrderBook.containsKey(symbol)) {
            return symbolToOrderBook.get(symbol).getBidAskSpread(currTime, age);
        } else {
            return new Pair<>(null, null);
        }
    }

    public Pair<OrderMetaData, OrderMetaData> getBidAskSpread(String symbol) {

        if (symbolToOrderBook.containsKey(symbol)) {
            return symbolToOrderBook.get(symbol).getBidAskSpread();
        } else {
            return new Pair<>(null, null);
        }
    }


    public void handleInput(String path) {

        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(path));

            String line = br.readLine();

            while (line != null) {
                String[] st = line.split(",");

                if (st.length != 5) {
                    System.out.printf("Invalid input, expect 5 tokens. This only has %s tokens. Malformed input is: %s%n", st.length, line);
                    line = br.readLine();
                    continue;
                }
                String source, symbol;
                BigInteger timestamp;
                double bidPrice, askPrice;

                try {
                    source = st[0];
                    symbol = st[1];
                    timestamp = new BigInteger(st[2]);
                    bidPrice = Double.parseDouble(st[3]);
                    askPrice = Double.parseDouble(st[4]);
                } catch (Exception e) {
                    System.out.println("Error parsing this input line: " + line);
                    line = br.readLine();
                    continue;
                }

                //If symbol do not have order book, create one for it.
                if (!symbolToOrderBook.containsKey(symbol)) {
                    symbolToOrderBook.put(symbol, new OrderBook(symbol));
                }

                OrderBook ob = symbolToOrderBook.get(symbol);
                OrderMetaData metaData = new OrderMetaData(source, symbol, timestamp, bidPrice, askPrice);

                ob.asks.ask.put(askPrice, metaData);
                ob.asks.numOfAsks++;
                ob.asks.totalAskPrice += askPrice;

                ob.bids.bid.put(bidPrice, metaData);
                ob.bids.numOfBids++;
                ob.bids.totalBidPrice += bidPrice;

                line = br.readLine();
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Error while opening file for input. Need to throw error and terminate. " + e);
        }
    }

    public String printEntireOrderBook(String symbol) {
        if (symbolToOrderBook.containsKey(symbol)) {
            String result = "Symbol: " + symbol + " entire Orderbook. \n";
            result += symbolToOrderBook.get(symbol).printEntireOrderBook();
            return result;
        } else {
            return "Symbol: " + symbol + " has no ASKS or BIDS associated with it.";
        }
    }

    public String printEntireOrderBook(String symbol, BigInteger age) {
        if (symbolToOrderBook.containsKey(symbol)) {
            String result = "Symbol: " + symbol + " entire Orderbook. \n";
            result += symbolToOrderBook.get(symbol).printEntireOrderBook(age);
            return result;
        } else {
            return "Symbol: " + symbol + " has no ASKS or BIDS associated with it.";
        }
    }


}