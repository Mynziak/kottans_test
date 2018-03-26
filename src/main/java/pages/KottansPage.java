package pages;

import core.MethodsFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;

public class KottansPage extends MethodsFactory {

    private By search_button = By.xpath("//button[text()='Search']");
    private By search_field = By.xpath("//input");
    private By searchResult_block = By.xpath("//*[@class='filters-container']");

    @Override
    public void isOpened() {
        waitUntilPageLoaded();
        assertTrue(isElementPresent(search_button, 60), "Kottans Page is not opened!");
    }

    public KottansPage search(String searchText) {
        assertTrue(isElementPresent(search_field), "Search input is absent!");
        type(search_field, searchText);
        click(search_button);
        assertTrue(isElementPresent(searchResult_block, 5), "Search Result is absent!");
        isOpened();
        return this;
    }

    public KottansPage sort(String sortValue) {
        WebElement sortSelect = driver.findElement(By.id("sort"));
        Select select = new Select(sortSelect);
        select.selectByVisibleText(sortValue);
        isOpened();
        return this;
    }

    private String getRepoName(String arg) {
        Pattern patt = Pattern.compile(":\\s*(.*)(?=Time:)");
        Matcher matcher = patt.matcher(arg);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            return null;
        }
    }

    public Map<String, Integer> getStarsMap() {
        By repo_container = By.xpath("//*[@class='repo']");
        assertTrue(isElementPresent(repo_container, 3), "No any repo have found!");
        Map<String, Integer> searchResMap = new HashMap<>();
        for (int i = 1; i <= driver.findElements(repo_container).size(); i++) {

            //extract repo Name:
            By repoName_xpath = By.xpath("(//div[@class='repo'])[" + i + "]/div");
            String repoName = getRepoName(driver.findElement(repoName_xpath).getText());
            assertNotEquals(repoName, null, "Name is not found!");
            assertNotEquals(repoName, "", "Name is absent!");

            //extract repo Stars:
            By repoStars_xpath = By.xpath("(//*[@class='repo']//div[@class='stars '])" + "[" + i + "]");
            Integer repoStars = Integer.parseInt(driver.findElement(repoStars_xpath).getText());
            assertNotEquals(repoStars, "", "Stars is absent!");
            searchResMap.put(repoName, repoStars);
        }
        return searchResMap;
    }
}
