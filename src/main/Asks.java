package main;

import java.util.ArrayList;
import java.util.TreeMap;

public class Asks {

    int numOfAsks;
    double totalAskPrice;
    TreeMap<Double, OrderMetaData> ask;

    public Asks() {
        numOfAsks = 0;
        totalAskPrice = 0.0;
        ask = new TreeMap<>();
    }
}
