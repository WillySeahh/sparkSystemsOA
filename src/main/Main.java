package main;

import java.math.BigInteger;
import java.util.*;

public class Main {

    private static HashMap<String, OrderBook> symbolToOrderBook = new HashMap<>();

    public static void main(String[] args) {

        FXSystem fxSystem = new FXSystem();
        fxSystem.handleInput(System.getProperty("user.dir") + "/src" + "/input.csv");

        fxSystem.symbolToOrderBook.forEach(
                ((s, orderBook) -> System.out.println(fxSystem.queryBidAskSpread("EURUSD", BigInteger.ZERO)))
        );

        System.out.println(fxSystem.queryBidAskSpread("SGD", new BigInteger("100000000000")));


        System.out.println(fxSystem.printEntireOrderBook("EURUSD"));
        System.out.println(fxSystem.printEntireOrderBook("EURUSD", new BigInteger("1000000000")));

    }
}
