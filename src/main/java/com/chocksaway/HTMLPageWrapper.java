package com.chocksaway;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by milesd on 13/08/15.
 */
public class HTMLPageWrapper extends HTMLPage {

    private static final String RIPE_READY = "ripe---ready";
    public HTMLPageWrapper(String url) {
        super(url);
    }


    /**
     * get some valid JSoup elements for a specific search filter
     * @param - Filter
     * @return - List of Elements
     */
    public List<Element> getPageElements(String tag) {
        return findValidElements(tag);
    }


    protected List<Element> cleanH3Elements(List<Element> elements) {
        Element marker = null;
        for (org.jsoup.nodes.Element each : elements) {
            if (each.childNodes().size() == 1) {
                marker = each;
            }
        }

        /*
            Remove the email H3
         */
        if (marker != null) {
            elements.remove(marker);
        }
        return elements;
    }



    /************************
     *  Get product titles
     * @param elements - Elements to search
     * @return List of product titles
     */
    protected List<String> getProductTitles(List<Element> elements) {
        List<String> listOfProducts = new ArrayList<String>();
        /*
            Check for RIPE_READY so we avoid the username / email address h3
         */
        elements.stream().filter(each -> each.childNodes().size() > 1).forEach(each -> {
            Node ele = each.childNodes().get(1);
                /*
                    Check for RIPE_READY so we avoid the username / email address h3
                 */
            if (ele.attributes().toString().contains(RIPE_READY)) {
                listOfProducts.add(
                        Utils.removeHTML(
                                StringEscapeUtils.unescapeHtml4(
                                        ele.childNodes().get(0).toString())
                        )
                );
            }
        });

        return listOfProducts;
    }

    /**
     * Get unit prices from main page
     * @param elements - Elements are searched
     * @return List of String values
     */
    protected List<String> getUnitPrices(Elements elements) {
        List<String> unitPrices = new ArrayList<String>();
        for (org.jsoup.nodes.Element each : elements) {
            String unitPriceAsString = Utils.removeHTML(each.childNodes().get(0).toString());
            unitPrices.add(Utils.parseCurrency(unitPriceAsString));
        }
        return unitPrices;
    }

    /**
     * Get product descriptions from child pages
     * @param elements - Elements are searched
     * @return - Descriptions
     */
    protected List<String> getDescriptionsInChildPages(List<Element> elements) {
        List<String> descriptions = elements.stream().map(each ->
                Utils.removeHTML(findDescriptionInChildDocument(
                        each.childNodes().get(1).attr("href"))))
                        .collect(Collectors.toList());
        return descriptions;
    }


    /**
     * Get page weights for each child page
     * @param elements - Search in Elements
     * @return - Page sizes
     */
    protected List<Integer> getPageWeights(List<Element> elements) {
        List<Integer> weights = elements.stream().map(each ->
                getPageWeight(
                        each.childNodes().get(1).attr("href")))
                .collect(Collectors.toList());
        return weights;
    }
}
