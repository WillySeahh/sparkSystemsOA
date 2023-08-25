package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class FXSystem {

    public HashMap<String, OrderBook> symbolToOrderBook = new HashMap<>();

    public String queryBidAskSpread(String symbol, BigInteger age) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYYYMMddHHmmssSSS");
        LocalDateTime now = LocalDateTime.now();
        BigInteger currTime = new BigInteger(dtf.format(now));

        return symbolToOrderBook.get(symbol).printBidAskSpreadWithinTimeFrame(currTime, age);

    }


    public void handleInput(String path) {

        //TODO explain why buffered reader is fasteest
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(path));

            String line = br.readLine();

            while (line != null) {
                String[] st = line.split(",");
                //StringTokenizer st = new StringTokenizer(line,","); //String tokenizer is faster

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
                    //TODO explain why dont let 1 malformed input break system
                    System.out.println("Error parsing this input line" + line);
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
            System.out.println("Error while opening file for input. Need to throw error and terminate" + e);
            e.printStackTrace();
        }
    }
}