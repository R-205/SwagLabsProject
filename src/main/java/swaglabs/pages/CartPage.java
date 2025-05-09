package swaglabs.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.locators.RelativeLocator;
import swaglabs.utils.ElementActions;
import swaglabs.utils.Waits;

import java.util.List;

public class CartPage {
    private WebDriver driver;
    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    //locators
    private By checkoutBtn = By.id("checkout");
    private By continueShoppingBtn = By.id("continue-shopping");
    private By verifycart = By.xpath("//span[@class=\"title\"]");



    //methods
    public HomePage cliclOncountinueShoppingBtn(){
        ElementActions.clickElement(driver,continueShoppingBtn);
        return new HomePage(driver);
    }

    public CheckoutOverviewPage cliclOnCheckoutBtn(){
        ElementActions.clickElement(driver,checkoutBtn);
        return new CheckoutOverviewPage(driver);
    }

    public String verifyingCartPage(){
        Waits.waitForElementPresent(driver,verifycart);
        return driver.findElement(verifycart).getText();
    }

    public CartPage removeFromCart(String productName) {
        By removeProduct = RelativeLocator.with(By.tagName("button")).below(By.xpath("//div[normalize-space()='" + productName + "']"));
        ElementActions.clickElement(driver, removeProduct);
        return this;
    }

    public boolean isProductNotInCart(String productName) {
        List<WebElement> cartItems = driver.findElements(By.className("inventory_item_name"));
        for (WebElement item : cartItems) {
            if (item.getText().equalsIgnoreCase(productName)) {
                return false; // لقينا المنتج، يعني لسه موجود
            }
        }
        return true; // مش لقيناه، يعني اتشال
    }

}




