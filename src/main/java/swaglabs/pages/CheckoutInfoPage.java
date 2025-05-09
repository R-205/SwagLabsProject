package swaglabs.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import swaglabs.utils.ElementActions;

public class CheckoutInfoPage {
    private WebDriver driver;

    public CheckoutInfoPage(WebDriver driver) {
        this.driver = driver;
    }
    //locators
    private By firstName= By.id("first-name");
    private By lastName= By.id("last-name");
    private By postalCode= By.id("postal-code");
    private By conCheckoutBtn= By.id("continue");
    private By cancelCheckoutBtn= By.id("cancel");
    private By errormessage= By.xpath("//h3[@data-test=\"error\"]");




    //methods

    public CheckoutInfoPage enterFirstName(String fname){
        ElementActions.sendData(driver,firstName,fname);
        return this;
    }

    public CheckoutInfoPage enterLastName(String lname){
        ElementActions.sendData(driver,lastName,lname);
        return this;
    }

    public CheckoutInfoPage enterPostalCode(String postalcod){
        ElementActions.sendData(driver,postalCode,postalcod);
        return this;
    }

    public CheckoutOverviewPage clickOnContinueCheckoutBtn(){
        ElementActions.clickElement(driver,conCheckoutBtn);
        return new CheckoutOverviewPage(driver);
    }

    public CheckoutOverviewPage clickOnCancelCheckoutBtn(){
        ElementActions.clickElement(driver,cancelCheckoutBtn);
        return new CheckoutOverviewPage(driver);
    }

    public String getErrorMessage(){
        return driver.findElement(errormessage).getText();
    }

}
