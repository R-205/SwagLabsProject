package listener;
import io.qameta.allure.Allure;
import org.openqa.selenium.WebDriver;
import org.testng.*;
import swaglabs.utils.LogsUtil;
import swaglabs.utils.ScreenShots;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Listeners implements IExecutionListener , ITestListener  {
    WebDriver driver;
    @Override
    public void onExecutionStart() {
        LogsUtil.info("Test Execution started");
    }

    /** Invoked once all the suites have been run. */
    @Override
    public void onExecutionFinish() {
        LogsUtil.info("Test Execution finished");
    }
    @Override
    public void onTestSuccess(ITestResult result) {
        LogsUtil.info("Test Case:" + result.getName()+"passed");

        WebDriver driver = (WebDriver) result.getAttribute("driver");
        if (driver != null) {

            ScreenShots.takeScreenShot(driver, "passed_" + result.getName());
        }

//        ScreenShots.takeScreenShot(driver,"passed"+result.getName());
    }

    /**
     * Invoked each time a test fails.
     *
     * @param result <code>ITestResult</code> containing information about the run test
     * @see ITestResult#FAILURE
     */
    @Override
    public void onTestFailure(ITestResult result) {
        LogsUtil.info("Test Case:" + result.getName()+"failed");
        WebDriver driver = (WebDriver) result.getAttribute("driver");
        if (driver != null) {
            ScreenShots.takeScreenShot(driver, "failed_" + result.getName());
        }

//        ScreenShots.takeScreenShot(driver,"failed"+result.getName());

    }



}
