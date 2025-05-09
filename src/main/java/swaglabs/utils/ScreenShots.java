package swaglabs.utils;

import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenShots {

    private static final String SCREEN_PATH = "test-outputs/ScreenShots/";
    //TODO:: Taking ScreenShots
    public static void takeScreenShot(WebDriver driver, String screenName) {
        try {
            File screen_src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File screen_Des = new File(SCREEN_PATH + screenName + "-" + getTimeStamp() + ".png");

            FileUtils.copyFile(screen_src, screen_Des);
            //Attach ScreenShot To Allure
            Allure.addAttachment(screenName, Files.newInputStream(Path.of(screen_Des.getPath())));
            //Files.newInputStream   يمعني ان ال الملف ده عندي في البروجكت مش برا

        } catch (Exception e) {
            e.printStackTrace();
        }

    }







    public static String getTimeStamp() {
        Date currentDate = new Date();
        return new SimpleDateFormat("yyyy-MM-dd-h-m-ss a").format(currentDate);
    }
}
