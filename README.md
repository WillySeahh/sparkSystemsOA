# Spark Systems OA

This readme contains my thought process and planning while attempting this assignment. 

## Description

1. There are multiple symbols, or for this case FX trading pairs, The Majors are the most traded
pairs of FX, EURUSD, USDJPY, GPD,USD, etc
   

2. For each of these symbols, there is a bid, ask price from multiple exchanges, we need to store the bids and asks.
Similar to an Orderbook (OB). Basically each symbol has its own OB. 
   
Use a hashmap to map symbol -> OB.
OB must contain 2 sorted maps, also called Treemaps. 

Maps must always contain a key, value pair. Since keys are sorted, we use price as the key, and values can contain the other
metadata, such as timestamp, source. 

This provides us an easy way to obtain the highest BID and lowest ASK, since its inherently sorted.

One drawback of using price as the key is that different exchanges/sources may have the same price
causing new the value to be overwritten. I thought this was alright since the focus is getting the highest bid and
lowest ask price, so overwriting the source and timestamp should be alright. If we assume that the timestamp is increasing, 
it is better to have a price reflect a 'more up to date ticker'. 

We could possibly store the timestamp as the key, but if 2 sources has the same timestamp, the 'correct' price may be overwritten.
Since the price is of concern here, I did not choose this method. 

## Input: 
Most of the input parameters are straight forward, for timestamp it is in this format:

```
2023 05 16 09 31 24 345 = 
2023 Year
05 Month
16 Day
09 Hour
31 Min 
345 is the miliseconds
```

We can take the absolute difference between queryTime and ticker time to find out if it satisfies the input query





Let us try to see how we can enahnce to accomodate the additional filters:

1. Filter prices for input symbol
```
This should be handled already since each symbol contains its own orderbook and via the orderbook
we can retrieve the bid-ask spread
```

2. Filter outliers that are more than x% off the average
```
For each symbol they should have a OB, OB has bid and asks. 
For each of the bid, ask, we can store the average. 
```

3. Filter prices according to time
```
This can be handled by creating another map that handles this, we should not pollute the original
design to achieve this purpose as this is probably for viewing purposes / not on hot path / critical path.
```

4. Filter according to sources
```
This can be handled by creating another map that handles this, we should not pollute the original
design to achieve this purpose as this is probably for viewing purposes / not on hot path / critical path.
```


