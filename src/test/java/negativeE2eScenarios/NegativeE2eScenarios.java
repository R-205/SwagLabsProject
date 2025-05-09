package negativeE2eScenarios;

import io.qameta.allure.*;
//import listener.TestListener;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestNGListener;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import swaglabs.driver.DriverFactory;
import swaglabs.pages.*;
import swaglabs.utils.DataUtil;
import swaglabs.utils.LogsUtil;
import swaglabs.utils.ScreenShots;
import swaglabs.utils.Scroll;

import java.io.IOException;
import java.lang.reflect.Method;

@Listeners({io.qameta.allure.testng.AllureTestNg.class, listener.Listeners.class})

public class NegativeE2eScenarios {
     WebDriver driver;
    SoftAssert softAssert= new SoftAssert();

    public WebDriver getDriver() {
        return driver;
    }

    @Severity(SeverityLevel.CRITICAL)
    @Owner("radwa")
    @Description("Verify that user can not login with invalid credentials, including incorrect passwords, locked-out accounts, and missing fields. The system must display appropriate error messages in each case.")
    @Test(priority = 1)
    public void invalidLogin() {
        Allure.step("Start Test: invalidLogin");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateTo()
                .enterUserName(DataUtil.getJsonData("testdata","invalid login credintial","username"))
                .enterPassword(DataUtil.getJsonData("testdata","invalid login credintial","password"))
                .logBtn();
        LogsUtil.info(" invalid login with invalid credintials.");


        Allure.step("Verify error message is displayed");
        softAssert.assertTrue(loginPage.isErrorMessageDisplayed(), "Expected error message, but none was displayed!");
        softAssert.assertEquals(loginPage.errorMessage(), loginPage.expectedErrorMessage(DataUtil.getJsonData("testdata","invalid login credintial","username"),DataUtil.getJsonData("testdata","invalid login credintial","password")), "Error message did not match expected!");
        softAssert.assertAll();

    }

    @Severity(SeverityLevel.CRITICAL)
    @Owner("radwa")
    @Description("Check that users cannot access the home page without logging in and receive the correct error message.")
    @Test(priority = 0)
    public void accessingHomePageWithoutLogin(){
        Allure.step("Start Test: accessingHomePageWithoutLogin");
        LogsUtil.info("Navigating directly to inventory page without login");
        driver.get("https://www.saucedemo.com/inventory.html");
        String currentUrl = driver.getCurrentUrl();
        LogsUtil.info("Checking if redirection to login page happened.");

        Allure.step("Verify if user is redirected to the login page");
        softAssert.assertTrue(currentUrl.contains("https://www.saucedemo.com"), "User should be redirected to the login page!");
        softAssert.assertEquals(new LoginPage(driver).errorMessage(),"Epic sadface: You can only access '/inventory.html' when you are logged in.");
        softAssert.assertAll();

        LogsUtil.info("Not allowed to Navigate directly to inventory page without login");
        Allure.step("Verified: Not allowed to access inventory page without login.");


    }


    @Severity(SeverityLevel.CRITICAL)
    @Owner("radwa")
    @Description("Verify that user can add the same product after reset the cart")
    @Test(priority = 6)
    public void checkAddTheSameProAfterReset() throws IOException {
        Allure.step("Start Test: checkAddTheSameProAfterReset");

        LoginPage login = new LoginPage(driver);
        HomePage home = new HomePage(driver);
        login.navigateTo()
                .enterUserName(DataUtil.getJsonData("testdata","login credintial","username"))
                .enterPassword(DataUtil.getJsonData("testdata","login credintial","password")).logBtn();
        softAssert.assertTrue(login.successfulLogin());
        LogsUtil.info("Adding first product to cart.");
        Allure.step("Add first product to cart");
        home.addToCard(DataUtil.getJsonData("testdata","product items","item1","product name"));
        softAssert.assertEquals( home.getNumberOfProductInCart(),"1");
        LogsUtil.info("Resetting app state from the menu.");
        Allure.step("Reset app state from the menu");

        home.clickonMenuButton().clickonResetAppStateButton().closeMenu();
        softAssert.assertEquals( home.getNumberOfProductInCart(),"0");
        LogsUtil.info("Re-adding the same product after reset.");
        Allure.step("Re-adding the same product after reset.");

        softAssert.assertEquals(home.verifyAddToCartBtn(DataUtil.getJsonData("testdata","product items","item1","product name")), "Add to cart", "Product button is not avalible to re-add  after reset.");

        home.addToCard(DataUtil.getJsonData("testdata","product items","item1","product name"));
        softAssert.assertEquals(home.verifyAddToCartBtn(DataUtil.getJsonData("testdata","product items","item1","product name")), "Remove", "Product is not re-added  after reset.");

        softAssert.assertEquals(home.getNumberOfProductInCart(), "1", "Product is not re-added  after reset.");
        softAssert.assertAll();
        Allure.step("Verify if the cart is updated correctly after reset, as it might be a site bug.");
        LogsUtil.info("Verify whether the cart has been updated correctly after the reset.");
    }

    @Severity(SeverityLevel.NORMAL)
    @Owner("radwa")
    @Description("Verify that user can proceed to checkout without adding any products to the cart.")

    @Test(priority = 7)
    public void checkCheckoutWithoutAnyProduct() throws IOException {

    Allure.step("Start Test: checkCheckoutWithoutAnyProduct");

    new LoginPage(driver).navigateTo()
            .enterUserName(DataUtil.getJsonData("testdata", "login credintial", "username"))
            .enterPassword(DataUtil.getJsonData("testdata", "login credintial", "password")).logBtn();
    softAssert.assertTrue(new LoginPage(driver).successfulLogin());

    Allure.step("Check if cart is empty before checkout");
    LogsUtil.info("Checking if cart is empty before checkout.");
    new HomePage(driver).clickonCartButton();
    softAssert.assertEquals(new HomePage(driver).getNumberOfProductInCart(), "0");
    softAssert.assertEquals(new CartPage(driver).verifyingCartPage(), "Your Cart");


    Allure.step("verify Clicking on checkout button without products in cart.");
    LogsUtil.info("Clicking on checkout button without products in cart.");

    new CartPage(driver).cliclOnCheckoutBtn();


    new CheckoutInfoPage(driver).enterFirstName(DataUtil.getJsonData("testdata", "checkoutdata", "data1", "firstname"))
            .enterLastName(DataUtil.getJsonData("testdata", "checkoutdata", "data1", "lastname"))
            .enterPostalCode(DataUtil.getJsonData("testdata", "checkoutdata", "data1", "postalcode")).clickOnContinueCheckoutBtn();

    Allure.step("Verify if the checkout process can be completed with no products");
    new CheckoutOverviewPage(driver).clickOnFinishBtn();
    Assert.assertNotEquals(new CheckoutOverviewPage(driver).verifyCompleteOrder(), "Thank you for your order!");
    Allure.step(" CheckOut process must not be completed without product ");
    ScreenShots.takeScreenShot(driver, "fail test");

    softAssert.assertAll();




    }

    @Severity(SeverityLevel.CRITICAL)
    @Owner("radwa")
    @Description("verify that users cannot proceed with the checkout process when essential data First Name is missing. The system should display appropriate error messages ")
    @Test(priority = 3)
    public void verifyCheckoutFormWithoutFname () throws IOException {
        Allure.step("Start Test: verifyCheckoutFormWithoutFirstName");

        new LoginPage(driver).navigateTo()
                .enterUserName(DataUtil.getJsonData("testdata","login credintial","username"))
                .enterPassword(DataUtil.getJsonData("testdata","login credintial","password")).logBtn();
        softAssert.assertTrue(new LoginPage(driver).successfulLogin());

        Allure.step("Add selected products to cart");
        new HomePage(driver).addToCard(DataUtil.getJsonData("testdata","product items","item1","product name"))
                .addToCard(DataUtil.getJsonData("testdata","product items","item3","product name")).clickonCartButton();

        softAssert.assertEquals(new HomePage(driver).getNumberOfProductInCart(),"2");
        LogsUtil.info("Products added to cart successfully.");
        softAssert.assertEquals(new CartPage(driver).verifyingCartPage(),"Your Cart");

        new CartPage(driver).cliclOnCheckoutBtn();
        LogsUtil.info("Navigated to checkout page.");

        Allure.step("Fill checkout form without First Name");
        new CheckoutInfoPage(driver).enterLastName(DataUtil.getJsonData("testdata","checkoutdata","data1","lastname"))
                .enterPostalCode(DataUtil.getJsonData("testdata","checkoutdata","data1","postalcode")).clickOnContinueCheckoutBtn();

        softAssert.assertTrue(new CheckoutInfoPage(driver).getErrorMessage().contains("First Name is required"));
        softAssert.assertAll();
        Allure.step(" error message should appear when First Name field is left empty during checkout.");

    }


    @Severity(SeverityLevel.CRITICAL)
    @Owner("radwa")
    @Description("verify that users cannot proceed with the checkout process when essential data Last Name is missing. The system should display appropriate error messages.")

@Test(priority = 4)
    public void verifyCheckoutFormWithoutLname() throws IOException{
        Allure.step("Start Test: verifyCheckoutFormWithoutLastName");

        new LoginPage(driver).navigateTo()
                .enterUserName(DataUtil.getJsonData("testdata","login credintial","username"))
                .enterPassword(DataUtil.getJsonData("testdata","login credintial","password")).logBtn();
        softAssert.assertTrue(new LoginPage(driver).successfulLogin());

        Allure.step("Add selected products to cart");
        new HomePage(driver).addToCard(DataUtil.getJsonData("testdata","product items","item1","product name"))
                .addToCard(DataUtil.getJsonData("testdata","product items","item3","product name")).clickonCartButton();
        softAssert.assertEquals(new HomePage(driver).getNumberOfProductInCart(),"2");
        LogsUtil.info("Products added to cart successfully.");

        softAssert.assertEquals(new CartPage(driver).verifyingCartPage(),"Your Cart");

        new CartPage(driver).cliclOnCheckoutBtn();
        LogsUtil.info("Navigated to checkout page.");

        Allure.step("Fill checkout form without Last Name");
        new CheckoutInfoPage(driver).enterFirstName(DataUtil.getJsonData("testdata","checkoutdata","data1","firstname"))
                .enterPostalCode(DataUtil.getJsonData("testdata","checkoutdata","data1","postalcode")).clickOnContinueCheckoutBtn();
        softAssert.assertTrue(new CheckoutInfoPage(driver).getErrorMessage().contains("Last Name is required"));
        softAssert.assertAll();
        Allure.step("verify error message should appear when Last Name field is left empty during checkout.");
    }


    @Severity(SeverityLevel.CRITICAL)
    @Owner("radwa")
    @Description("verify that users cannot proceed with the checkout process when Postal Code is missing. The system should display appropriate error messages for each missing field.")
    @Test(priority = 5)
    public void verifyCheckoutFormWithoutPostalCode() throws IOException{
        Allure.step("Start Test: verifyCheckoutFormWithoutPostalCode");

        new LoginPage(driver).navigateTo()
                .enterUserName(DataUtil.getJsonData("testdata","login credintial","username"))
                .enterPassword(DataUtil.getJsonData("testdata","login credintial","password")).logBtn();
        softAssert.assertTrue(new LoginPage(driver).successfulLogin());

        Allure.step("Add selected products to cart");
        new HomePage(driver).addToCard(DataUtil.getJsonData("testdata","product items","item1","product name"))
                .addToCard(DataUtil.getJsonData("testdata","product items","item3","product name")).clickonCartButton();
        softAssert.assertEquals(new HomePage(driver).getNumberOfProductInCart(),"2");
        LogsUtil.info("Products added to cart successfully.");
        softAssert.assertEquals(new CartPage(driver).verifyingCartPage(),"Your Cart");


        new CartPage(driver).cliclOnCheckoutBtn();
        LogsUtil.info("Navigated to checkout page.");

        Allure.step("Fill checkout form without Postal Code");
        new CheckoutInfoPage(driver).enterFirstName(DataUtil.getJsonData("testdata","checkoutdata","data1","firstname"))
                .enterLastName(DataUtil.getJsonData("testdata","checkoutdata","data1","lastname")).clickOnContinueCheckoutBtn();
        softAssert.assertTrue(new CheckoutInfoPage(driver).getErrorMessage().contains("Postal Code is required"));
        softAssert.assertAll();
        LogsUtil.info("Verified error message for missing Postal Code.");


        Allure.step("verify  error message should appear when Postal Code field is left empty during checkout. ");
    }



    @Severity(SeverityLevel.CRITICAL)
    @Owner("radwa")
    @Description("Verify that the user cannot access the home page using the browser's Back button after logging out. The system should redirect to the login page with an error message.")
    @Test(priority = 2)
    public void NavigatBackAfterLogout() throws IOException {
        Allure.step("Start test: Verify navigation back after logout");

        new LoginPage(driver).navigateTo()
                .enterUserName(DataUtil.getJsonData("testdata", "login credintial", "username"))
                .enterPassword(DataUtil.getJsonData("testdata", "login credintial", "password"))
                .logBtn();
        Assert.assertTrue(new LoginPage(driver).successfulLogin());
        Allure.step("Navigated to Login Page and Entered username and password and clicked login");

        new HomePage(driver).navigateToHomePage()
                .clickonMenuButton()
                .clickonLogoutButton();
        Allure.step("User clicked menu and logged out");

        Assert.assertEquals(new LoginPage(driver).alreadyLogout(), "Accepted usernames are:");
        Allure.step("Verified logout success message displayed");
        LogsUtil.info("sucessful logout");

        driver.navigate().back();
        Allure.step("Navigated back in browser after logout");

        Assert.assertEquals(new LoginPage(driver).errorMessage(), "Epic sadface: You can only access '/inventory.html' when you are logged in.");
        LogsUtil.info("error message when navigating back after logout");
        Allure.step("Verified error message when navigating back after logout");
        LogsUtil.info("End test: Navigation back after logout handled correctly");
    }


//    @BeforeMethod
//    public void set() {
//        DriverFactory driverFactory= new DriverFactory();
//        driver=driverFactory.setDriver("chrome");
////        LogsUtil.debug("Driver initialized successfully");
//
//    }


@BeforeMethod
public void setUp(Method method, ITestResult result) {
    DriverFactory driverFactory = new DriverFactory();
    driver = driverFactory.setDriver("chrome");

    // حفظ الـ WebDriver في result
    result.setAttribute("driver", driver);
}



    @AfterMethod
    public void close() {
        driver.quit();
    }
}
