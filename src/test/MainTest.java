package test;

import javafx.util.Pair;
import main.FXSystem;
import main.Main;
import main.OrderBook;
import main.OrderMetaData;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class MainTest {


    @Test
    public void testGetBestAskOrBid() {

        FXSystem fxSystem = new FXSystem();
        fxSystem.handleInput(System.getProperty("user.dir") + "/src/test" + "/testInput1.csv");

        Assertions.assertEquals(fxSystem.symbolToOrderBook.size(), 1);
        Assertions.assertNotNull(fxSystem.symbolToOrderBook.get("EURUSD"));
        Assertions.assertNull(fxSystem.symbolToOrderBook.get("EURSGD"));

        OrderBook ob = fxSystem.symbolToOrderBook.get("EURUSD");
        Pair<OrderMetaData, OrderMetaData> pair = ob.getBidAskSpread();

        Assertions.assertNotNull(pair);
        OrderMetaData lowestAsk = pair.getKey();
        OrderMetaData highestBid = pair.getValue();

        Assertions.assertEquals(lowestAsk.askPrice, 1.20);


        Assertions.assertEquals(highestBid.bidPrice, 1.40);


    }
}
