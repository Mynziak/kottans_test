package core;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * Created by Dima on 06.06.2016.
 */
public class BrowseFactory extends MethodsFactory {

    public static final String PATH_TO_WIN_CHROME_DRIVER = new File("src/main/resources/drivers/chromedriver.exe").getAbsolutePath();

    @BeforeTest
    @Parameters({"browser"})
    public void setupBrowser(@Optional("CH") String browser) {

        if (browser.equalsIgnoreCase("FF")) {
            //TODO: set path to ffdriver
            myDriver = new FirefoxDriver();
        } else if (browser.equalsIgnoreCase("CH")) {
            System.setProperty("webdriver.chrome.driver", PATH_TO_WIN_CHROME_DRIVER);
            myDriver = new ChromeDriver();
        }
        driver = new EventFiringWebDriver(myDriver);
        driver.register(new DriverListener());
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(60, TimeUnit.SECONDS); //wait for finishing scripts
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS); //wait for
    }

    @AfterTest
    public void tearDown() {
        driver.close();
        driver.quit();
    }

    @Override
    public void isOpened() {

    }
}
