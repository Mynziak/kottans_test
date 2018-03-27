package tests;

import com.google.common.collect.ImmutableMap;
import core.BrowseFactory;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.KottansPage;
import pages.enums.LanguageValues;
import pages.enums.SortingValues;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class KottansTest extends BrowseFactory {
    private String kottansUrl = "https://xstoneriderx.github.io/kottans/#/";
    private String textForSearch = "test";
    private String textPdffiller = "pdffiller";
    private KottansPage kottansPage;
    private String matlabRepoName = "Test--01";
    private List<String> repoWithIssuesList = Arrays.asList("HelloWorld", "rokehan", "sNews", "Test--01");
    private List<String> expectedContributersList = Arrays.asList("rs", "matej", "lavoy");

    //set expected sorted map by name:
    private Map<String, Integer> sortedMapByName = ImmutableMap.of(
            "HelloWorld", 0,
            "rokehan", 0,
            "SDWebImage", 1,
            "sNews", 1,
            "Test--01", 0
    );

    //set expected sorted map by stars:
    private Map<String, Integer> sortedMapByStars = ImmutableMap.of(
            "HelloWorld", 0,
            "rokehan", 0,
            "Test--01", 0,
            "SDWebImage", 1,
            "sNews", 1
    );

    @BeforeTest
    public void openKottansPage() {
        getUrl(kottansUrl);
        kottansPage = new KottansPage();
        kottansPage.isOpened();

        //1. Do search
        kottansPage.search(textForSearch);
    }

    @Test
    public void sortTest() {

        //1. Get repo Name and stars:
        Map<String, Integer> searchResMap = kottansPage.getStarsMap();

        //2. Sort by Stars and get repo Name, stars
        kottansPage.sort(SortingValues.STARS.toString());
        Map<String, Integer> sortResMap = kottansPage.getStarsMap();

        //3. Compare sorting results:
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(searchResMap, sortedMapByName, "Sort by Name works incorrect!");
        softAssert.assertEquals(sortResMap, sortedMapByStars, "Sort by Stars works incorrect!");
        softAssert.assertAll();
    }

    @Test
    public void languageFilterTest() {

        //1. Select language
        kottansPage.selectLanguage(LanguageValues.MATLAB.toString());

        Map<String, Integer> resultMap;
        //2. get result
        resultMap = kottansPage.getStarsMap();

        //3. Compare actual result with expected:
        assertEquals(resultMap.keySet().toArray()[0], matlabRepoName, "Language menu filters incorrect!");

        //4. return filter back:
        kottansPage.selectLanguage(LanguageValues.ALL.toString());

        //5. Get Result:
        resultMap = kottansPage.getStarsMap();

        //5. Compare actual result with expected:
        assertEquals(resultMap, sortedMapByName, "Language menu filters incorrect!");
    }

    @Test
    public void issuesFilterTest() {

        //1. Check Has Issues checkbox:
        kottansPage.selectIssuesCheckBox(true);

        //2. Get result:
        Map<String, Integer> hasIssuesResMap;
        hasIssuesResMap = kottansPage.getStarsMap();

        //3. Compare sorting results:
        assertEquals(hasIssuesResMap.size(), repoWithIssuesList.size(), "Filter Has issues works incorrect!");

        for (Map.Entry entry : hasIssuesResMap.entrySet()) {
            assertTrue(repoWithIssuesList.contains(entry.getKey()), "Filter Has issues works incorrect!");
        }

        //4. Uncheck Has Issues checkbox:
        kottansPage.selectIssuesCheckBox(false);

        //5. Get result:
        hasIssuesResMap = kottansPage.getStarsMap();

        //6. Compare filter results:
        assertEquals(hasIssuesResMap, sortedMapByName, "Filter Has issues works incorrect!");
    }

    @Test
    public void topicsFilterTest() {

        //1. Check Has topics checkbox:
        kottansPage.selectTopicsCheckbox(true);

        //2. Check that Nothing To Show is displayed
        assertTrue(kottansPage.isNothingToShowDisplayed(), "Has topics filters incorrect!");

        //3. Do search
        kottansPage.search(textPdffiller);

        //4. Check Has topics checkbox:
        kottansPage.selectTopicsCheckbox(true);

        //3. Get result:
        Map<String, Integer> hasTopicsResMap;
        hasTopicsResMap = kottansPage.getStarsMap();

        //4. Compare filter results:
        assertEquals(hasTopicsResMap.size(), 4, "Filter Has topics works incorrect!");
    }

    @Test
    public void starsFilterTest() {

        //1. Filter by stars:
        kottansPage.enterStarNumber(4);

        //2. Check that Nothing To Show is displayed
        assertTrue(kottansPage.isNothingToShowDisplayed(), "Stars filters incorrect!");

        //3. Filter by stars:
        kottansPage.enterStarNumber(1);

        //4. Compare filter results:
        Map<String, Integer> starsResMap;
        starsResMap = kottansPage.getStarsMap();
        assertEquals(starsResMap.size(), 2, "Filter Stars works incorrect!");
    }

    @Test
    public void contributersNamesTest() {

        //1. Select repo:
        kottansPage.selectRepoByName("SDWebImage");

        //2. Get contributers list
        List<String> contributorsList = kottansPage.getContributers();

        //3. Compare result:
        assertEquals(contributorsList, expectedContributersList);

        //4. close repo:
        kottansPage.closeRepo();
    }
}