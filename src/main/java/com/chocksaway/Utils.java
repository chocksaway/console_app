package com.chocksaway;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;

/**
 * Created by milesd on 14/08/15.
 * Generic Utils class
 */
public class Utils {
    static Logger logger = LoggerFactory.getLogger(Utils.class);

    /**
     * remove HTML and trim pre and post spaces
     * @param str - text to be cleaned
     * @return - cleaned text
     */
    protected static String removeHTML(String str) {
        String dirty = Jsoup.parse(str).text();
        String better=dirty.replace("\"", "");
        String cleaned = better.trim();

        return cleaned;
    }

    /**
     * Parse currency - default UK pound, and two decimal spaces
     * @param curr - Parse unit cost currency
     * @return - currency two decimal places
     */
    protected static String parseCurrency(String curr) {
        DecimalFormat numberFormat = new DecimalFormat("¤#.00", new DecimalFormatSymbols(Locale.UK));
        Number num;
        String twoDecPlaces = null;
        try {
            num = numberFormat.parse(curr);

            DecimalFormat df = new DecimalFormat("#.00");

            twoDecPlaces = df.format(num);
        } catch (ParseException e) {
            logger.info(e.getMessage());
        }

        return twoDecPlaces;
    }


    protected static String toJson(List<StockItem> items) {
        DataObject obj = new DataObject(items);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        // convert java object to JSON format,
        // and returned as JSON formatted string
        String json = gson.toJson(obj);

        return json;
    }
}



class DataObject {
    List <StockItem> results;

    public DataObject(List <StockItem> stockItems) {
        results = stockItems;
    }

    //getter and setter methods

    @Override
    public String toString() {
        return "DataObject [list="+ results + "]";
    }

}
