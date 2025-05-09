package swaglabs.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class Scroll {


    public static void scrollToElement(WebDriver driver, By locator) {

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", Waits.waitForElementPresent(driver, locator));

    }
}
