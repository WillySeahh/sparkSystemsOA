package test;

import javafx.util.Pair;
import main.FXSystem;
import main.Main;
import main.OrderBook;
import main.OrderMetaData;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

public class MainTest {


    @Test
    public void testGetBestAskOrBid() {

        /**
         * Using testInput2. Input as testInput1, testing if we can get the correct highest bid and lowest ask.
         */

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


    @Test
    public void testInvalidFile() {

        /**
         * Input as testInput1, testing if we can get the correct highest bid and lowest ask.
         */

        FXSystem fxSystem = new FXSystem();
        //fxSystem.handleInput(System.getProperty("user.dir") + "/src/test" + "/missingInput.csv");

        try {
            fxSystem.handleInput(System.getProperty("user.dir") + "/src/test" + "/missingInput.csv");
        } catch (Exception e) {
            Assertions.assertEquals(e, FileNotFoundException.class);
        }
    }

    @Test
    public void testGetBestAskOrBidWithInvalidRows() {

        /**
         * Using testInput2. Same Input as testInput1, just that testInput2 has malformed rows, which will be ignored.
         * Hence the assertions and price should be same as testInput1.
         * Ensure that 1 malformed line do not break the entire system.
         */

        FXSystem fxSystem = new FXSystem();
        fxSystem.handleInput(System.getProperty("user.dir") + "/src/test" + "/testInput2.csv");

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


    @Test
    public void testGetBestAskOrBidWithMultipleSymbols() {

        /**
         * Using testInput3. Similar as testInput1, but testing with multiple symbols. EURUSD and GBPUSD
         */

        FXSystem fxSystem = new FXSystem();
        fxSystem.handleInput(System.getProperty("user.dir") + "/src/test" + "/testInput3.csv");

        Assertions.assertEquals(fxSystem.symbolToOrderBook.size(), 2);
        Assertions.assertNotNull(fxSystem.symbolToOrderBook.get("EURUSD"));
        Assertions.assertNull(fxSystem.symbolToOrderBook.get("EURSGD"));

        OrderBook ob = fxSystem.symbolToOrderBook.get("EURUSD");
        Pair<OrderMetaData, OrderMetaData> pair = ob.getBidAskSpread();

        Assertions.assertNotNull(pair);
        OrderMetaData lowestAsk = pair.getKey();
        OrderMetaData highestBid = pair.getValue();

        Assertions.assertEquals(lowestAsk.askPrice, 1.20);

        Assertions.assertEquals(highestBid.bidPrice, 1.40);

        Assertions.assertNotNull(fxSystem.symbolToOrderBook.get("GBPUSD"));

        ob = fxSystem.symbolToOrderBook.get("GBPUSD");
        pair = ob.getBidAskSpread();

        Assertions.assertNotNull(pair);
        lowestAsk = pair.getKey();
        highestBid = pair.getValue();

        Assertions.assertEquals(lowestAsk.askPrice, 1.20);

        Assertions.assertEquals(highestBid.bidPrice, 1.40);
    }

}
