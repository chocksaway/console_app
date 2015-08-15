package com.chocksaway;

/**
 * Created by milesd on 12/08/15.
 */
public class StockItem {
    String title;
    int size;
    String unit_price;   // using _ as part of spec
    String description;

    public StockItem(String title, int size, String unitPrice, String description) {
        this.title = title;
        this.size = size;
        this.unit_price = unitPrice;
        this.description = description;

    }
}
