package swaglabs.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ElementActions {
    public static void clickElement(WebDriver driver , By locator) {
        Waits.elementToBeClickable(driver, locator);
//        Scroll.scrollToElement(driver, locator);
        driver.findElement(locator).click();

    }

    public static void sendData(WebDriver driver, By locator, String text) {
        Waits.waitForElementVisible(driver, locator);
//        Scroll.scrollToElement(driver, locator);
        driver.findElement(locator).sendKeys(text);
    }


    public static String getText(WebDriver driver, By locator){
        Waits.waitForElementVisible(driver, locator);
        Scroll.scrollToElement(driver, locator);
        return driver.findElement(locator).getText();


    }

    public static void clearData(WebDriver driver, By locator) {
        Waits.waitForElementVisible(driver, locator);
        Scroll.scrollToElement(driver, locator);
        driver.findElement(locator).clear();
    }

    public static boolean isIconClickable(WebDriver driver,By locator) {
        WebElement icon = driver.findElement(locator);
        return icon.isEnabled() && icon.isDisplayed();
    }
}
