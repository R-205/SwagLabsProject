package TestPages;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import swaglabs.driver.DriverFactory;
import swaglabs.pages.LoginPage;
import swaglabs.utils.ScreenShots;


public class LoginTest {
    WebDriver driver;

    SoftAssert softAssert = new SoftAssert();

    @DataProvider
    public Object[][] validData() {
        return new Object[][] {
                {"standard_user","secret_sauce"}
        };
    }

    @Test(dataProvider = "validData")
    public void validLogin(String username, String password) {
//        driver.get("https://www.saucedemo.com/v1/index.html");
        new LoginPage(driver).navigateTo().enterUserName(username).enterPassword(password).logBtn();
         LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.successfulLogin());
        ScreenShots.takeScreenShot(driver,"validLogin");

  }



//
    @DataProvider
    public Object[][] invalidData() {
        return new Object[][]{
                {"standard_user", "wrong_password"},
                {"locked_out_user", "secret_sauce"},
                {"", "secret_sauce"},
                {"standard_user", ""}
        };
    }


    @Test(dataProvider = "invalidData")
    public void invalidLogin(String username, String password) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateTo().enterUserName(username).enterPassword(password).logBtn();
        softAssert.assertTrue(loginPage.isErrorMessageDisplayed(), "Expected error message, but none was displayed!");
        softAssert.assertEquals(loginPage.errorMessage(), loginPage.expectedErrorMessage(username, password), "Error message did not match expected!");
        softAssert.assertAll();

    }

    @DataProvider
    public Object[][] usersWithProblems() {
        return new Object[][]{
                {"problem_user", "secret_sauce"},
                {"performance_glitch_user", "secret_sauce"}

        };
    }


    @Test(dataProvider = "usersWithProblems")
    public void unexpectedLogin(String username, String password) {
        new LoginPage(driver).navigateTo().enterUserName(username).enterPassword(password).logBtn();
        LoginPage loginPage = new LoginPage(driver);
        Assert.assertTrue(loginPage.successfulLogin(),"unexpected success login with "+username);
    }


    @BeforeTest
    public void set() {

        DriverFactory driverFactory = new DriverFactory();
        driver = driverFactory.setDriver("edge");

    }

    @AfterTest
    public void close() {
        driver.quit();
    }
}
