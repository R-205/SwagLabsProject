package swaglabs.pages;

import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import swaglabs.utils.ElementActions;
import swaglabs.utils.JavaExecuter;
import swaglabs.utils.Waits;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class HomePage {

    private WebDriver driver;
    private By allProductname = By.className("inventory_item_name");
    private By menuButton =By.xpath("//div[@class=\"bm-cross-button\"]//button\n");
    private By closemenuButton =By.xpath("//div[@class='bm-burger-button']/button");
    private By logoutBtn = By.id("logout_sidebar_link");
    private By allItemsBtn = By.id("inventory_sidebar_link");
    private By aboutBtn = By.id("about_sidebar_link");
    private By resetBtn = By.id("reset_sidebar_link");
    private By cartButton = By.id("shopping_cart_container");
    private By homepage = By.xpath("//span[@class=\"title\"]");
    private By linkedinIcon = By.xpath("//a[@data-test=\"social-linkedin\"]");
    private By xIcon = By.xpath("//a[@data-test=\"social-twitter\"]");
    private By facebookIcon = By.xpath("//a[@data-test=\"social-facebook\"]");
    private By numbersofPro0 = By.xpath("//span[@class=\"shopping_cart_badge\"]");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    //Actions...
    public HomePage navigateToHomePage() {
        return this;
    }

    public String verifyingHomePage(){
        Waits.waitForElementPresent(driver,homepage);
        return driver.findElement(homepage).getText();
    }

    public HomePage clickonMenuButton(){
        JavaExecuter.javaexecuter(driver,menuButton);
        return this;
    }

    public HomePage clickonallitemsButton(){
        ElementActions.clickElement(driver,allItemsBtn);
        return this;
    }

    public HomePage clickonAboutButton(){
        ElementActions.clickElement(driver,aboutBtn);
        return this;
    }

    public LoginPage clickonLogoutButton(){
        ElementActions.clickElement(driver,logoutBtn);
        return new LoginPage(driver);
    }

    public HomePage clickonResetAppStateButton(){
        ElementActions.clickElement(driver,resetBtn);
        return this;
    }

    public HomePage closeMenu(){
        JavaExecuter.javaexecuter(driver,closemenuButton);
        return this;
    }

    public HomePage addToCard(String productName) {
        By addProduct = RelativeLocator.with(By.tagName("button")).below(By.xpath("//div[normalize-space()='" + productName + "']"));
        ElementActions.clickElement(driver, addProduct);
        return this;
    }

    public String verifyAddToCartBtn(String productName) {
        By addProduct = RelativeLocator.with(By.tagName("button")).below(By.xpath("//div[normalize-space()='" + productName + "']"));
        return driver.findElement(addProduct).getText() ;
    }



    public HomePage selectOptionFromDropdown(String optionText) {

        WebElement dropdown = driver.findElement(By.xpath("//select[@data-test=\"product-sort-container\"]"));
        Select select = new Select(dropdown);
        select.selectByVisibleText(optionText);
        return this;

    }

    public List<String> getAllProductNames() {
        List<WebElement> productElements = driver.findElements(allProductname);
        List<String> productNames = new ArrayList<>();

        for (WebElement element : productElements) {
            productNames.add(element.getText().trim());
        }

        return productNames;
    }



    public void clickonFacebookIcon() {
    ElementActions.clickElement(driver,facebookIcon);
    }

    public void clickonTwitterIcon() {
        ElementActions.clickElement(driver,xIcon);
    }
    public void clickonLinkedinIcon() {
        ElementActions.clickElement(driver,linkedinIcon);
    }



    public String getNumberOfProductInCart() {
        List<WebElement> badgeElements = driver.findElements(numbersofPro0);

        if (badgeElements.isEmpty()) {
            return "0"; // مفيش منتجات في السلة
        } else {
            return badgeElements.get(0).getText().trim(); // بيرجع رقم المنتجات الظاهر على البيدج
        }
    }


    public CartPage clickonCartButton(){
        ElementActions.clickElement(driver,cartButton);
        return new CartPage(driver);
    }

    public boolean verifyHomePage(){

        return driver.findElement(By.xpath("//span[@class=\"title\"]")).getText().contains("Products");
    }



    public String getNewWindowHandle(WebDriver driver, String originalWindow) {
        Set<String> allWindows = driver.getWindowHandles();

        String newWindow = null;
        for (String window : allWindows) {
            if (!window.equals(originalWindow))
            {
                newWindow = window;
                break;
            }
        }

        return newWindow;
    }











}
