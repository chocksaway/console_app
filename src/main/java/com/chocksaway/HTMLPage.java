package com.chocksaway;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by milesd on 13/08/15.
 */
public class HTMLPage {
    protected static final String HEADER_3 = "h3";
    private static final String UNIT_PRICE = "p[class=pricePerUnit]";
    private static final String DESCRIPTION = "div[class=memo] p";

    private static final String ALT_DESCRIPTION = "div[class=section] productcontent htmlcontent div[class=productText]";

    Logger logger = LoggerFactory.getLogger(HTMLPage.class);

    private Document document;
    private Connection.Response response;
    private String url;

    /*
        Cache for pages already created
     */
    private Map<String, HTMLPage> pageCache;

    public HTMLPage(String url) {
        this.url = url;
    }

    private void parseHTML() {
        this.document = connectAndGetJSoupDocument();
    }


    /**
     * JSoup connect and return document
     * @return JSoup document
     */
    private Document connectAndGetJSoupDocument() {
        Document doc = null;
        try {
            response = Jsoup
                    .connect(url)
                    .method(Connection.Method.GET)
                    .followRedirects(true)
                    .execute();

            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            logger.info(e.getMessage());
        }

        return doc;
    }

    /**
     * Find valid elements (with child element(s)
     * @param filter - HTML tag
     * @return Valid elements which have child nodes
     */
    protected List<Element> findValidElements(String filter) {
        parseHTML();
        List<Element> elementList = new ArrayList<Element>();
        Elements elements = document.select(filter);

        elementList.addAll(elements.stream().filter(each -> each.childNodes().size() > 0).collect(Collectors.toList()));

        return elementList;
    }


    /**
     * @return HEADER - H3
     */
    protected String getH3() {
        return HEADER_3;
    }


    /**
     * Find unit prices in parent HTML page
     * @return Elements for all unit prices
     */
    protected Elements findUnitPriceInDocument() {
        parseHTML();
        return document.select(UNIT_PRICE);
    }

    /**
     * Check cache for url
     * @param url - url is the key for the cache
     * @return HTMLPage
     */
    private HTMLPage isPageInCache(String url) {
        if (pageCache == null) {
            pageCache = new HashMap<String, HTMLPage>();
        }

        HTMLPage child;
        if (pageCache.containsKey(url)) {
            child = pageCache.get(url);
        } else {
            child = new HTMLPage(url);
            child.parseHTML();
            pageCache.put(url,child);
        }
        return child;
    }



    /**
     * Get the description from the child product page
     * @param childLevelPage - cache key (url)
     * @return Description
     */
    protected String findDescriptionInChildDocument(String childLevelPage) {
        HTMLPage child = isPageInCache(childLevelPage);

        /*
            For some products there seems to be a more generic product description
         */
        Elements ele = child.document.select(DESCRIPTION);
        if (ele.size() == 0 ) {
            ele = child.document.select(ALT_DESCRIPTION);
            logger.info("alternate description");
            return Utils.removeHTML(ele.get(0).toString());
        }

        String htmlRemoved = Utils.removeHTML(ele.get(0).toString());

        logger.info("HTML removed " + htmlRemoved);

        return htmlRemoved;
    }


    protected int getPageWeight(String childLevelPage) {
        HTMLPage child = isPageInCache(childLevelPage);

        return child.response.bodyAsBytes().length;
    }
}
