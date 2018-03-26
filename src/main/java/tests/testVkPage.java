package tests;

import core.MethodsFactory;
import org.openqa.selenium.By;

/**
 * Created by Dima on 06.07.2016.
 */
public class testVkPage extends MethodsFactory {

    public By accountInfoTab = By.cssSelector("img[alt='Account Information']");
    public By personalInfoTab = By.cssSelector("img[alt='Personal Information']");
    public By activeTab = By.cssSelector(".td_active");
    public By attributionMenu = By.id("attribution");
    public By fullName = By.id("accountUserName");


    public boolean isOpen(){
        return isElementPresent(By.cssSelector(".account-page"), 30);
    }

    public boolean isOpenAccountInfoTab (){
        click(accountInfoTab);
        return isElementDisplayed(activeTab, 30);
    }
}
