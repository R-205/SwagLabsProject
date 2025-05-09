package swaglabs.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.html5.AddWebStorage;

public class JavaExecuter {
    public static void javaexecuter(WebDriver driver, By locator) {
        Waits.waitForElementPresent(driver, locator);

        WebElement element = driver.findElement(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

}
