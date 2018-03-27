package core;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.*;
import utils.Logger;

import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * Created by Dima on 06.06.2016.
 */
public class BrowseFactory extends MethodsFactory {

    public static final String PATH_TO_WIN_CHROME_DRIVER = new File("src/main/resources/drivers/chromedriver.exe").getAbsolutePath();

    @BeforeTest
    @Parameters({"browser"})
    protected void beforeTest(@Optional("CH") String browser) {
        if (browser.equalsIgnoreCase("FF")) {
            //TODO: set path to ffdriver
            myDriver = new FirefoxDriver();
        } else if (browser.equalsIgnoreCase("CH")) {
            System.setProperty("webdriver.chrome.driver", PATH_TO_WIN_CHROME_DRIVER);
            myDriver = new ChromeDriver();
        } else {
            Logger.info("Start without driver.");
        }
        driver = new EventFiringWebDriver(myDriver);
        driver.register(new DriverListener());
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(60, TimeUnit.SECONDS); //wait for finishing scripts
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS); //wait for
    }

    @AfterTest
    protected void tearDown() {
        driver.close();
        driver.quit();
    }

    @Override
    public void isOpened() {
    }
}
