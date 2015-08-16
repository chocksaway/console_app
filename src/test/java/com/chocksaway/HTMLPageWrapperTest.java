package com.chocksaway;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by milesd on 14/08/15.
 */
public class HTMLPageWrapperTest {

    private static final String AVOCADO = "Avocado";
    private HTMLPageWrapper wrapper;

    Logger logger = LoggerFactory.getLogger(HTMLPage.class);


    /*
        http://www.sainsburys.co.uk/webapp/wcs/stores/servlet/CategoryDisplay?
        listView=true& orderBy=FAVOURITES_FIRST&parent_category_rn=12518&
        top_category=12518&langId=44
        &beginIndex=0&pageSize=20
        &catalogId=10137&searchTerm=&categoryId=1857 49
        &listId=&storeId=10151&promotionId=#langId=44
        &storeId=10151&catalogId=10137&categoryId=185749
        &parent_category_rn=12518&top_category=12518&pageSize=20
        &orderBy=FAVOURITES_FIRST&searchTerm=&beginIndex=0&hideFilters=true
     */

    static final String TOP_LEVEL_URL = "http://www.sainsburys.co.uk/webapp/wcs/stores/servlet/CategoryDisplay?" +
            "listView=true& orderBy=FAVOURITES_FIRST&parent_category_rn=12518&" +
            "top_category=12518&langId=44" +
            "&beginIndex=0&pageSize=20" +
            "&catalogId=10137&searchTerm=&categoryId=185749" +
            "&listId=&storeId=10151&promotionId=#langId=44" +
            "&storeId=10151&catalogId=10137&categoryId=185749" +
            "&parent_category_rn=12518&top_category=12518&pageSize=20" +
            "&orderBy=FAVOURITES_FIRST&searchTerm=&beginIndex=0&hideFilters=true";


    @Before
    public void oneTimeSetUp() {
        wrapper = new HTMLPageWrapper(TOP_LEVEL_URL);

    }

    /**
     * Get Title from main page
     */

    @Test
    public void getMainPageIndividualProductTitleInformation() {
        List<Element> elements = wrapper.getPageElements(wrapper.getH3());
        assertTrue(elements.size() > 0);

        List<String> strList = wrapper.getProductTitles(elements);

        // todo this will fail of AVOCADO is not in main page - mock in future
        assertTrue(strList.stream().filter(item -> item.contains(AVOCADO))
                        .collect(
                                Collectors.toList()).size() > 0
        );
    }


    /**
     * Get the unit prices for products
     */

    @Test
    public void getMainPageUnitPrices() {
        Elements elements = wrapper.findUnitPriceInDocument();
        assertTrue(elements.size() > 0);
        List<BigDecimal> unitPrices = wrapper.getUnitPrices(elements);
        assertTrue(unitPrices.stream().filter(item -> item.signum() > 0)
                        .collect(
                                Collectors.toList()).size() > 0
        );
    }




    /**
     * Get product description from child pages
     */

    @Test
    public void getChildPageProductDescription() {
        List<Element> elements = wrapper.getPageElements(wrapper.getH3());
        assertTrue(elements.size() > 0);
        elements = wrapper.cleanH3Elements(elements);
        List<String> descriptions = wrapper.getDescriptionsInChildPages(elements);
        assertTrue(descriptions.size() == 12);

    }


    /**
     * Get child page size in kb
     */

    @Test
    public void getChildPageLength() {
        List<Element> elements = wrapper.getPageElements(wrapper.getH3());
        assertTrue(elements.size() > 0);
        elements = wrapper.cleanH3Elements(elements);
        List<String> weights = wrapper.getPageWeights(elements);
        assertTrue(weights.size() == 12);
    }


    /**
     * Get StockItems
     */

    @Test
    public void buildStocksItems() {
        Elements elements = wrapper.findUnitPriceInDocument();
        assertTrue(elements.size() > 0);
        List<BigDecimal> unitPrices = wrapper.getUnitPrices(elements);
        assertTrue(unitPrices.size() == 12);

        List<Element> ele = wrapper.getPageElements(wrapper.getH3());
        ele = wrapper.cleanH3Elements(ele);
        assertTrue(ele.size() > 0);

        List<String> titles = wrapper.getProductTitles(ele);
        assertTrue(titles.size() == 12);

        List<String> descriptions = wrapper.getDescriptionsInChildPages(ele);
        assertTrue(descriptions.size() == 12);
        List<String> weights = wrapper.getPageWeights(ele);
        assertTrue(weights.size() == 12);

        List<StockItem> items = new ArrayList<StockItem>();
        BigDecimal total = BigDecimal.ZERO;
        for (int counter = 0; counter < 12; counter++) {
            StockItem item = new StockItem(titles.get(counter),
                    weights.get(counter),
                    unitPrices.get(counter),
                    descriptions.get(counter));
            items.add(item);
            total = total.add(unitPrices.get(counter));
        }

        String json = Utils.toJson(items, total);

        System.out.print("---------- Welcome to the JSon Console:--------");
        System.out.println(json);
        System.out.println("---------- End of Console Output --------");

        assertNotNull(json);




    }
}