package core;

/**
 * Created by Dima on 06.06.2016.
 */

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import sun.rmi.runtime.Log;

public class StaticDriver {
    protected static WebDriver myDriver;
    public static EventFiringWebDriver driver;

    protected static String mwh; //Main Window handle
    public static Browser browser;
}
