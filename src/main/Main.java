package main;

import java.math.BigInteger;
import java.util.*;

public class Main {

    private static HashMap<String, OrderBook> symbolToOrderBook = new HashMap<>();

    public static void main(String[] args) {

        FXSystem fxSystem = new FXSystem();
        fxSystem.handleInput(System.getProperty("user.dir") + "/src" + "/input.csv");

        fxSystem.symbolToOrderBook.forEach(
                ((s, orderBook) -> System.out.println(orderBook.printBidAskSpread()))
        );

        System.out.println(fxSystem.queryBidAskSpread("EURUSD", new BigInteger("100000000000")));

    }

}
