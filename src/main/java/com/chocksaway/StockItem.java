package com.chocksaway;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by milesd on 12/08/15.
 */
public class StockItem {
    String title;
    String size;
    BigDecimal unit_price;   // using _ as part of spec
    String description;

    public StockItem(String title, String size, BigDecimal unitPrice, String description) {
        this.title = title;
        this.size = size;
        this.unit_price = unitPrice;
        this.description = description;
    }
}