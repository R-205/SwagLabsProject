package swaglabs.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import swaglabs.utils.ElementActions;
import swaglabs.utils.LogsUtil;
import swaglabs.utils.Waits;

public class LoginPage {

    //locators
    private WebDriver driver;

    private By username = By.id("user-name");
    private By password = By.id("password");
    private By loginBtn = By.id("login-button");
    private By log = By.xpath("//div[@id=\"login_credentials\"]//h4\n");
    private By logoutBtn = By.id("logout_sidebar_link");




    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }
   
    //Actions...
    public LoginPage navigateTo() {
//        driver.get("https://www.saucedemo.com/v1/index.html");
        https://www.saucedemo.com/
        driver.get("https://www.saucedemo.com/");

        return this;
    }
    public LoginPage enterUserName(String username) {
        ElementActions.sendData(driver,this.username,username);
        return this;

    }

    public LoginPage enterPassword(String password) {
        ElementActions.sendData(driver,this.password, password);
        return this;
    }

    public HomePage logBtn() {
        ElementActions.clickElement(driver, this.loginBtn);
        return new HomePage(driver);
    }

    public String alreadyLogout() {
        Waits.waitForElementPresent(driver,log);
        return driver.findElement(log).getText();
    }
    public String alreadyLogoutafterBack() {
        Waits.waitForElementPresent(driver,logoutBtn);
        return driver.findElement(logoutBtn).getText();
    }




    public boolean successfulLogin(){
        LogsUtil.info("login sucessfully");

        return driver.findElement(By.xpath("//span[@class=\"title\"]")).getText().contains("Products");

//        try {
//            Waits.waitForElementPresent(driver, By.xpath("//div[contains(@class,'product_label')]"));
//            return driver.findElement(By.xpath("//div[contains(@class,'product_label')]")).getText().contains("Products");
//        } catch (Exception e) {
//            return false;
//        }

    }

    public boolean isErrorMessageDisplayed() {
        try {
            return driver.findElement(By.cssSelector("h3[data-test='error']")).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public  String errorMessage() {
        return driver.findElement(By.xpath("//h3[@data-test=\"error\"]")).getText();

    }

    public String expectedErrorMessage(String username, String password) {
        if (username.equals("standard_user") && password.equals("wrong_password")) {
            return "Epic sadface: Username and password do not match any user in this service";
        } else if (username.equals("locked_out_user")) {
            return "Epic sadface: Sorry, this user has been locked out.";
        } else if (username.isEmpty()) {
            return "Epic sadface: Username is required";
        } else if (password.isEmpty()) {
            return "Epic sadface: Password is required";
        } else {
            return "Epic sadface: Username and password do not match any user in this service";
        }
    }










}
