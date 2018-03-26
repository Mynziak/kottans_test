package tests;

import core.BrowseFactory;
import org.testng.annotations.Test;
import pages.KottansPage;
import pages.SortingValues;

import java.util.Map;

public class KottansTest extends BrowseFactory {

    private String kottansUrl = "https://xstoneriderx.github.io/kottans/#/";
    private String textForSearch = "Mynziak";

    @Test
    public void kottansTest() {
        getUrl(kottansUrl);
        KottansPage kottansPage = new KottansPage();
        kottansPage.isOpened();
        kottansPage.search(textForSearch)
                .sort(SortingValues.STARS.toString());
        Map<String, Integer> searchResMap = kottansPage.getStarsMap();
        System.out.println("");

    }

    @Test
    public void SortTest() {
}
