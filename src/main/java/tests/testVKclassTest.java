package tests;

import core.BrowseFactory;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

/**
 * Created by Dima on 06.07.2016.
 */
public class testVKclassTest extends BrowseFactory {

    public By emailField =By.id("form-login-email");
    public By passField =By.id("form-login-password");
    public By logInOrangeBtn=By.id("form-login-submit");



    @Test
    public void checkServicesPage (){

        getUrl("https://dev15.pdffiller.com/en/top.htm");

    }
}
