package core;


import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Dima on 06.07.2016.
 */
public class ListenerTests extends TestListenerAdapter {

    public void onTestStart(ITestResult result){
        result.getName();
        System.out.println( result.getName()+"========================START TESTING ==================================");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        File scrFile = ((TakesScreenshot) BrowseFactory.driver).getScreenshotAs(OutputType.FILE);
        //The below method will save the screen shot in C drive with name "HH_mm_ss.png"
        DateFormat df1 = new SimpleDateFormat("\\dd_MM_yy\\");
        DateFormat df2 = new SimpleDateFormat("HH_mm_ss");
        Calendar calobj = Calendar.getInstance();
        String folderName = df1.format(calobj.getTime());
        String screenshotName = df2.format(calobj.getTime());
        String path1 = "C:\\FailedScreenshots\\";
        String screen1 = path1 +folderName+screenshotName +".png";
        try {
            FileUtils.copyFile(scrFile, new File(screen1));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(result.getName()+ "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!---Test failed---!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println(result.getName()+"++++++++++++++Test success++++++++++++");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
    }

    @Override
    public void onFinish(ITestContext result) {
    }
}
