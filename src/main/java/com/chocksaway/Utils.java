package com.chocksaway;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.List;

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
    protected static BigDecimal parseCurrency(String curr) {
        String val = curr.replace("£", "");
        return new BigDecimal(val);
    }


    protected static String toJson(List<StockItem> items, BigDecimal total) {
        DataObject obj = new DataObject(items, total);

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .create();

        // convert java object to JSON format,
        // and returned as JSON formatted string
        String json = gson.toJson(obj);

        return json;
    }


    /**
     * Write console JSon to file
     * @param json - output
     */
    protected static void writeToFile(String json) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("console.json", "UTF-8");
            writer.println(json);
            writer.close();
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }
    }
}



class DataObject {
    List <StockItem> results;
    BigDecimal total;

    public DataObject(List <StockItem> stockItems, BigDecimal total) {
        this.results = stockItems;
        this.total = total;
    }

    //getter and setter methods

    @Override
    public String toString() {
        return "DataObject [list="+ results + "]," + total;
    }

}
