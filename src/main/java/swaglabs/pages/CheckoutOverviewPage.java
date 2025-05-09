package swaglabs.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import swaglabs.utils.ElementActions;
import swaglabs.utils.Waits;

import java.util.ArrayList;
import java.util.List;

public class CheckoutOverviewPage {
    private WebDriver driver;

    public CheckoutOverviewPage(WebDriver driver) {
        this.driver = driver;
    }
     //locators
    private By finishBtn= By.id("finish");
    private By cancelBtn = By.id("cancel");
    private By finishOrder = By.xpath("//h2[@class=\"complete-header\"]");
    private By total = By.xpath("//div[@class=\"summary_total_label\"]");
    private By allProductprice = By.className("inventory_item_price");
    private By verifyCheckoutOverview = By.xpath("//span[@class=\"title\"]");

    //methods

public CheckoutOverviewPage clickOnFinishBtn(){
    ElementActions.clickElement(driver,finishBtn);
    return this;
}

    public HomePage clickOnCancelBtn(){
        ElementActions.clickElement(driver,cancelBtn);
        return new HomePage(driver);
    }

    public String verifyCompleteOrder(){
        Waits.waitForElementVisible(driver,finishOrder);
        return driver.findElement(finishOrder).getText();
    }

    public List<Double> getAllProductPricesAsNumbers() {
        List<WebElement> priceElements = driver.findElements(allProductprice);
        List<Double> productPrices = new ArrayList<>();

        for (WebElement element : priceElements) {
            String priceText = element.getText().replace("$", "").trim();
            productPrices.add(Double.parseDouble(priceText));
        }

        return productPrices;
    }


    public double getItemTotalFromCheckout() {
        String totalText = driver.findElement(total).getText();
        String numberOnly = totalText.replace("Total: $", "").trim();
        return Double.parseDouble(numberOnly);
    }


    public double expectedTotalWithTax(List<Double> prices) {
        double expectedTotal = 0.0;

        for (Double price : prices) {
            expectedTotal += price;
        }
        double expectedTotalWithTax = expectedTotal + (expectedTotal * 0.08);
        return expectedTotalWithTax;
    }

    public String verifyingCheckOutPage(){
        Waits.waitForElementPresent(driver,verifyCheckoutOverview);
        return driver.findElement(verifyCheckoutOverview).getText();
    }

}
