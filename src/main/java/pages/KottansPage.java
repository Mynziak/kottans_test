package pages;

import core.MethodsFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.testng.Assert.assertFalse;
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

    public KottansPage selectLanguage(String language) {
        WebElement languageSelect = driver.findElement(By.id("language"));
        Select select = new Select(languageSelect);
        select.selectByVisibleText(language);
        isOpened();
        return this;
    }

    //Regex function for extracting name:
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
        Map<String, Integer> searchResMap = new HashMap<>();
        if (isElementPresent(repo_container, 3)) {
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
        } else {
            searchResMap.put(null, null);
        }
        return searchResMap;
    }

    public KottansPage selectIssuesCheckBox(boolean doCheck) {
        By hasIssues_checkbox = By.id("issues");
        assertTrue(isElementPresent(hasIssues_checkbox), "Has Issues checkbox is absent!");
        if (doCheck) {

            // check Has Issues checkbox:
            if (!driver.getCurrentUrl().contains("issues")) {
                click(hasIssues_checkbox);
            }
            assertTrue(driver.getCurrentUrl().contains("issues"), "Has Issues checkbox is not checked!");
        } else {

            //uncheck Has Issues checkbox
            if (driver.getCurrentUrl().contains("issues")) {
                click(hasIssues_checkbox);
            }
            assertFalse(driver.getCurrentUrl().contains("issues"), "Has Issues checkbox is not unchecked!");
        }
        isOpened();
        return this;
    }

    public KottansPage selectTopicsCheckbox(boolean doCheck) {
        By hasTopics_checkbox = By.id("topics");
        assertTrue(isElementPresent(hasTopics_checkbox), "Has topics checkbox is absent!");
        if (doCheck) {
            // check Has topics checkbox:
            if (!driver.getCurrentUrl().contains("topics")) {
                click(hasTopics_checkbox);
            }
            assertTrue(driver.getCurrentUrl().contains("topics"), "Has topics checkbox is not checked!");
        } else {

            //uncheck Has topics checkbox
            if (driver.getCurrentUrl().contains("topics")) {
                click(hasTopics_checkbox);
            }
            assertFalse(driver.getCurrentUrl().contains("topics"), "Has topics checkbox is not unchecked!");
        }
        isOpened();
        return this;
    }

    public boolean isNothingToShowDisplayed() {
        By nothingToShow = By.xpath("//*[text()='Nothing to show :(']");
        return isElementPresent(nothingToShow);
    }

    public KottansPage enterStarNumber(int starNumber) {
        By star_input = By.id("starred");
        assertTrue(isElementPresent(star_input), "Star input field is absent!");
        type(star_input, String.valueOf(starNumber));
        isOpened();
        return this;
    }

    public void selectRepoByName(String repoName) {
        By repoContainer = By.xpath("//*[@class='repo']//*[contains(., '" + repoName + "')]");
        assertTrue(isElementPresent(repoContainer, 5), "Repo with name [" + repoName + "] is absent!");
        click(repoContainer);
        By link_xpath = By.xpath("//*[@href='https://github.com/test/" + repoName + "']");
        assertTrue(isElementPresent(link_xpath, 5), "Repo container is not opened!");
    }

    public List<String> getContributers() {
        By allContributer_xpath = By.xpath("//*[@class='contributors']");
        assertTrue(isElementPresent(allContributer_xpath), "Contributors are absent!");
        List<String> contributersList = new ArrayList<>();

        for (int i = 1; i <= driver.findElements(allContributer_xpath).size(); i++) {
            By contributer_xpath = By.xpath("((//*[@class='contributors'])[" + i + "]//a)[2]");
            String contributerName = driver.findElement(contributer_xpath).getText();
            contributersList.add(contributerName);
        }
        return contributersList;
    }

    public KottansPage closeRepo() {
        By close_button = By.xpath("//*[@class='close-btn']");
        assertTrue(isElementPresent(close_button), "Close button is absent!");
        click(close_button);
        isOpened();
        return this;
    }
}

