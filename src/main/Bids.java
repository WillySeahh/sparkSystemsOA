package main;

import java.util.ArrayList;
import java.util.TreeMap;

public class Bids {

    int numOfBids;
    double totalBidPrice;
    TreeMap<Double, OrderMetaData> bid;

    public Bids() {
        numOfBids = 0;
        totalBidPrice = 0.0;
        bid = new TreeMap<>();
    }

}
