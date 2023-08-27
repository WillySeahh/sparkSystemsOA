package main;

import java.math.BigInteger;

public class Main {


    public static void main(String[] args) {

        FXSystem fxSystem = new FXSystem();
        fxSystem.handleInput(System.getProperty("user.dir") + "/src" + "/input.csv");

        fxSystem.symbolToOrderBook.forEach(
                ((s, orderBook) -> System.out.println(fxSystem.queryBestBidAsk("EURUSD", BigInteger.ZERO)))
        );

        System.out.println(fxSystem.queryBestBidAsk("SGD", new BigInteger("100000000000")));

//        System.out.println(fxSystem.queryBestBidAsk("SGD", new BigInteger("100000000000")));
//
//
        System.out.println(fxSystem.printEntireOrderBook("EURUSD"));
        System.out.println(fxSystem.printEntireOrderBook("EURUSD", new BigInteger("1000000000")));

    }
}
