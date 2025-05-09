package positive;

import io.qameta.allure.*;
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

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;


@Listeners({io.qameta.allure.testng.AllureTestNg.class, listener.Listeners.class})
public class PositiveE2eScenarios {

    WebDriver driver;
    SoftAssert softAssert = new SoftAssert();



    @Severity(SeverityLevel.CRITICAL)
    @Owner("radwa")
    @Description("Verify that the user can add all product to the cart and checkout successfully")
    @Test
    public void addingAllProductsToCart() throws IOException {
        Allure.step("Start Test: Adding All Products to Cart");
        new LoginPage(driver).navigateTo()
                .enterUserName(DataUtil.getJsonData("testdata","login credintial","username"))
                .enterPassword(DataUtil.getJsonData("testdata","login credintial","password")).logBtn();
        softAssert.assertTrue(new LoginPage(driver).successfulLogin());

        Allure.step("Adding all six products to the cart");
        new HomePage(driver).addToCard(DataUtil.getJsonData("testdata","product items","item1","product name"))
                .addToCard(DataUtil.getJsonData("testdata","product items","item2","product name"))
                .addToCard(DataUtil.getJsonData("testdata","product items","item3","product name"))
                .addToCard(DataUtil.getJsonData("testdata","product items","item4","product name"))
                .addToCard(DataUtil.getJsonData("testdata","product items","item5","product name"))
                .addToCard(DataUtil.getJsonData("testdata","product items","item6","product name")).clickonCartButton();
        softAssert.assertEquals(new HomePage(driver).getNumberOfProductInCart(),"6");
        softAssert.assertEquals(new CartPage(driver).verifyingCartPage(),"Your Cart");
        LogsUtil.info("all products are added to cart");

        new CartPage(driver).cliclOnCheckoutBtn();

        Allure.step("Filling in checkout information");
        new CheckoutInfoPage(driver).enterFirstName(DataUtil.getJsonData("testdata","checkoutdata","data1","firstname"))
                .enterLastName(DataUtil.getJsonData("testdata","checkoutdata","data1","lastname"))
                .enterPostalCode(DataUtil.getJsonData("testdata","checkoutdata","data1","postalcode")).clickOnContinueCheckoutBtn();

        Allure.step("Verifying total price with tax");
        List<Double> prices = new CheckoutOverviewPage(driver).getAllProductPricesAsNumbers();
        double actualTotal = new CheckoutOverviewPage(driver).getItemTotalFromCheckout();
        softAssert.assertEquals(actualTotal, new CheckoutOverviewPage(driver).expectedTotalWithTax(prices), 0.01, "Item total mismatch!");
        Allure.step("Completing the order");
        new CheckoutOverviewPage(driver).clickOnFinishBtn();
        softAssert.assertEquals(new CheckoutOverviewPage(driver).verifyCompleteOrder(),"Thank you for your order!");
        LogsUtil.info("checkout successfully");
        Allure.step("Checkout completed successfully");
        softAssert.assertAll();


    }



    @Severity(SeverityLevel.CRITICAL)
    @Owner("radwa")
    @Description("Verify that the user can add a specific product to the cart and proceed to checkout and complete the order successfully")
    @Test
    public void addSpecificItemToCard()  throws IOException {
        Allure.step("Start Test: Adding specific Product to Cart");

        new LoginPage(driver).navigateTo()
                .enterUserName(DataUtil.getJsonData("testdata","login credintial","username"))
                .enterPassword(DataUtil.getJsonData("testdata","login credintial","password")).logBtn();
        softAssert.assertTrue(new LoginPage(driver).successfulLogin());

        Allure.step("Adding specific product to the cart");

        new HomePage(driver).addToCard(DataUtil.getJsonData("testdata","product items","item1","product name")).clickonCartButton();
        softAssert.assertEquals(new HomePage(driver).getNumberOfProductInCart(),"1");
        softAssert.assertEquals(new CartPage(driver).verifyingCartPage(),"Your Cart");
        LogsUtil.info("product is added to cart");

        new CartPage(driver).cliclOnCheckoutBtn();

        Allure.step("Filling in checkout information");
        new CheckoutInfoPage(driver).enterFirstName(DataUtil.getJsonData("testdata","checkoutdata","data1","firstname"))
                .enterLastName(DataUtil.getJsonData("testdata","checkoutdata","data1","lastname"))
                .enterPostalCode(DataUtil.getJsonData("testdata","checkoutdata","data1","postalcode")).clickOnContinueCheckoutBtn();

        Allure.step("Verifying total price with tax");
        List<Double> prices = new CheckoutOverviewPage(driver).getAllProductPricesAsNumbers();
        double actualTotal = new CheckoutOverviewPage(driver).getItemTotalFromCheckout();
        softAssert.assertEquals(actualTotal, new CheckoutOverviewPage(driver).expectedTotalWithTax(prices), 0.01, "Item total mismatch!");


        new CheckoutOverviewPage(driver).clickOnFinishBtn();
        LogsUtil.info("checkout successfully");
        softAssert.assertEquals(new CheckoutOverviewPage(driver).verifyCompleteOrder(),"Thank you for your order!");
        Allure.step("Checkout completed successfully");
        softAssert.assertAll();


    }


    @Severity(SeverityLevel.NORMAL)
    @Owner("radwa")
    @Description("Verify that a user can add a product to the cart and proceed to checkout page and user can cancel the order after proceeding to checkout")
    @Test
    public void cancelProcessAfterAddingProduct() throws IOException {
        Allure.step("Start Test: Cancel Process After Adding Product");

        new LoginPage(driver).navigateTo()
                .enterUserName(DataUtil.getJsonData("testdata","login credintial","username"))
                .enterPassword(DataUtil.getJsonData("testdata","login credintial","password")).logBtn();
        softAssert.assertTrue(new LoginPage(driver).successfulLogin());
        Allure.step("Adding product to the cart");

        new HomePage(driver).addToCard(DataUtil.getJsonData("testdata","product items","item1","product name")).clickonCartButton();
        softAssert.assertEquals(new HomePage(driver).getNumberOfProductInCart(),"1");
        softAssert.assertEquals(new CartPage(driver).verifyingCartPage(),"Your Cart");
        LogsUtil.info("product is added to cart");
        Allure.step("Proceeding with checkout and cancelling");
        new CartPage(driver).cliclOnCheckoutBtn();
        new CheckoutInfoPage(driver).enterFirstName(DataUtil.getJsonData("testdata","checkoutdata","data1","firstname"))
                .enterLastName(DataUtil.getJsonData("testdata","checkoutdata","data1","lastname"))
                .enterPostalCode(DataUtil.getJsonData("testdata","checkoutdata","data1","postalcode")).clickOnContinueCheckoutBtn();
        new CheckoutOverviewPage(driver).clickOnCancelBtn();
        softAssert.assertEquals(new HomePage(driver).verifyingHomePage(),"Products");
        LogsUtil.info("cancel order successfully");

        softAssert.assertAll();
    }


    @Test
    public void CheckRemoveProductFromCart() throws IOException {
        Allure.step("Start Test: Remove Product From Cart");
        new LoginPage(driver).navigateTo()
                .enterUserName(DataUtil.getJsonData("testdata","login credintial","username"))
                .enterPassword(DataUtil.getJsonData("testdata","login credintial","password")).logBtn();
        softAssert.assertTrue(new LoginPage(driver).successfulLogin());

        Allure.step("Adding Two products to the cart");
        new HomePage(driver).addToCard(DataUtil.getJsonData("testdata","product items","item1","product name")).
                addToCard(DataUtil.getJsonData("testdata","product items","item2","product name"))
                .clickonCartButton();
        softAssert.assertEquals(new HomePage(driver).getNumberOfProductInCart(),"2");
        LogsUtil.info("two products are added to cart");

        Allure.step("Removing one product from the cart");
        new CartPage(driver).removeFromCart(DataUtil.getJsonData("testdata","product items","item2","product name"));
        softAssert.assertEquals(new HomePage(driver).getNumberOfProductInCart(),"1");
        softAssert.assertTrue(new CartPage(driver).isProductNotInCart(DataUtil.getJsonData("testdata","product items","item2","product name")));
        LogsUtil.info("one of product is removed from the cart successfully");

        new CartPage(driver).cliclOnCheckoutBtn();
        Allure.step("Filling in checkout information");
        new CheckoutInfoPage(driver).enterFirstName(DataUtil.getJsonData("testdata","checkoutdata","data1","firstname"))
                .enterLastName(DataUtil.getJsonData("testdata","checkoutdata","data1","lastname"))
                .enterPostalCode(DataUtil.getJsonData("testdata","checkoutdata","data1","postalcode"))
                .clickOnContinueCheckoutBtn();


        Allure.step("Verifying total price with tax");
        List<Double> prices = new CheckoutOverviewPage(driver).getAllProductPricesAsNumbers();
        double actualTotal = new CheckoutOverviewPage(driver).getItemTotalFromCheckout();
        softAssert.assertEquals(actualTotal, new CheckoutOverviewPage(driver).expectedTotalWithTax(prices), 0.01, "Item total mismatch!");

        Allure.step("Completing the order");
        new CheckoutOverviewPage(driver).clickOnFinishBtn();
        softAssert.assertEquals(new CheckoutOverviewPage(driver).verifyCompleteOrder(),"Thank you for your order!");
        softAssert.assertAll();
    }


    @Severity(SeverityLevel.CRITICAL)
    @Owner("radwa")
    @Description("Verify that after resetting the app state after adding a product , the user can add another product to the cart.")
    @Test
    public void checkAddAnotherProAfterReset() throws IOException {
        Allure.step("Start Test: Add another product after resetting cart");
        new LoginPage(driver).navigateTo()
                .enterUserName(DataUtil.getJsonData("testdata","login credintial","username"))
                .enterPassword(DataUtil.getJsonData("testdata","login credintial","password")).logBtn();
        softAssert.assertTrue(new LoginPage(driver).successfulLogin());

        Allure.step("Adding first product to the cart");
        new HomePage(driver).addToCard(DataUtil.getJsonData("testdata","product items","item1","product name"));
        softAssert.assertEquals( new HomePage(driver).getNumberOfProductInCart(),"1");
        softAssert.assertEquals(new HomePage(driver).verifyAddToCartBtn(DataUtil.getJsonData("testdata","product items","item2","product name")), "Add to cart", "Product is not re-added  after reset.");

        LogsUtil.info("product is added to cart");
        Allure.step("Resetting app state and adding another product");
        new HomePage(driver).clickonMenuButton().clickonResetAppStateButton().closeMenu();
        softAssert.assertEquals( new HomePage(driver).getNumberOfProductInCart(),"0");
        new HomePage(driver).addToCard(DataUtil.getJsonData("testdata","product items","item2","product name"));
        softAssert.assertEquals( new HomePage(driver).getNumberOfProductInCart(),"1");
        LogsUtil.info("reset the cart then add another product successfully");
        Allure.step(" adding the another product successfully");
        softAssert.assertAll();
    }


    @Description("Verify that the user can continue shopping after adding a product to the cart and clicking on 'Continue Shopping' button. The user should be redirected to the home page and be able to add more items to the cart.")
    @Owner("Radwa")
    @Test
    public void checkContinueShopping() throws IOException {
        Allure.step("Start Test: Continue Shopping After Adding Product");
        new LoginPage(driver).navigateTo()
                .enterUserName(DataUtil.getJsonData("testdata","login credintial","username"))
                .enterPassword(DataUtil.getJsonData("testdata","login credintial","password")).logBtn();
        softAssert.assertTrue(new LoginPage(driver).successfulLogin());

        Allure.step("Adding first product and navigating to cart");
        new HomePage(driver).addToCard(DataUtil.getJsonData("testdata","product items","item1","product name")).clickonCartButton();
        softAssert.assertEquals(new HomePage(driver).getNumberOfProductInCart(),"1");
        LogsUtil.info("product is added to cart");
        Allure.step("Adding second product after continuing shopping");

        new CartPage(driver).cliclOncountinueShoppingBtn();
        softAssert.assertTrue(new HomePage(driver).verifyHomePage());
        LogsUtil.info("go to the home page and continue the shopping ");
        Allure.step("go to the home page and continue the shopping ");

        new HomePage(driver).addToCard(DataUtil.getJsonData("testdata","product items","item2","product name")).clickonCartButton();
        softAssert.assertEquals(new HomePage(driver).getNumberOfProductInCart(),"2");
        softAssert.assertAll();

    }

    @Severity(SeverityLevel.NORMAL)
    @Owner("radwa")
    @Description("Verify that clicking the Twitter icon opens the FaceBook page in a new tab.")
    @Test
    public void verifyFacebookIconFun() throws IOException{
        Allure.step("Start Test: Verify Facebook Icon Opens in New Tab");
        new LoginPage(driver).navigateTo()
                .enterUserName(DataUtil.getJsonData("testdata","login credintial","username"))
                .enterPassword(DataUtil.getJsonData("testdata","login credintial","password")).logBtn();
        Assert.assertTrue(new LoginPage(driver).successfulLogin());

        Allure.step("Clicking on Facebook icon and verifying new window");
        HomePage home = new HomePage(driver);


        String originalWindow = driver.getWindowHandle();
        home.clickonFacebookIcon();
        String newWindow = home.getNewWindowHandle(driver, originalWindow);
        if (newWindow != null) {
            driver.switchTo().window(newWindow);

            Assert.assertTrue(driver.getCurrentUrl().contains("facebook"), "The Facebook page did not open in a new tab.");
            LogsUtil.info("a new window is opened (Facebook Page) ");

        } else {
            Assert.fail("No new window was opened.");
        }

        driver.switchTo().window(originalWindow);
    }


    @Severity(SeverityLevel.NORMAL)
    @Owner("radwa")
    @Description("Verify that clicking the LinkedIn icon opens the LinkedIn page in a new tab.")
    @Test
    public void verifyLinkedinIconFun() throws IOException {
        Allure.step("Start Test: Verify LinkedIn Icon Opens in New Tab");

        new LoginPage(driver).navigateTo()
                .enterUserName(DataUtil.getJsonData("testdata","login credintial","username"))
                .enterPassword(DataUtil.getJsonData("testdata","login credintial","password")).logBtn();
        Assert.assertTrue(new LoginPage(driver).successfulLogin());

        Allure.step("Clicking on LinkedIn icon and verifying new window");

        HomePage home = new HomePage(driver);
        String originalWindow = driver.getWindowHandle();
        home.clickonLinkedinIcon();
        String newWindow = home.getNewWindowHandle(driver, originalWindow);
        if (newWindow != null) {
            driver.switchTo().window(newWindow);
            Assert.assertTrue(driver.getCurrentUrl().contains("https://www.linkedin.com/company/sauce-labs/"), "The linkedin page did not open in a new tab.");
            LogsUtil.info("a new window is opened (LinkedIn Page) ");

        } else {
            Assert.fail("No new window was opened.");
        }
        driver.switchTo().window(originalWindow);
    }

    @Severity(SeverityLevel.NORMAL)
    @Owner("radwa")
    @Description("Verify that clicking the Twitter icon opens the Twitter page in a new tab.")
    @Test
    public void verifyTwitterIconFun() throws IOException {
        Allure.step("Start Test: Verify Twitter Icon Opens in New Tab");

        new LoginPage(driver).navigateTo()
                .enterUserName(DataUtil.getJsonData("testdata","login credintial","username"))
                .enterPassword(DataUtil.getJsonData("testdata","login credintial","password")).logBtn();
        Assert.assertTrue(new LoginPage(driver).successfulLogin());


        Allure.step("Clicking on Twitter icon and verifying new window");
        HomePage home = new HomePage(driver);
        String originalWindow = driver.getWindowHandle();
        home.clickonTwitterIcon();
        String newWindow = home.getNewWindowHandle(driver, originalWindow);
        if (newWindow != null) {
            driver.switchTo().window(newWindow);
            Assert.assertTrue(driver.getCurrentUrl().contains("x"), "The x page did not open in a new tab.");
            LogsUtil.info("a new window is opened (twitter Page) ");

        } else {
            Assert.fail("No new window was opened.");
        }
        driver.switchTo().window(originalWindow);
    }



    @Description("Verify that the 'About' button directs the user to the Sauce Labs page.")
    @Severity(SeverityLevel.NORMAL)
    @Owner("radwa")
    @Test(dependsOnMethods = {"verifyFacebookIconFun","verifyTwitterIconFun","verifyLinkedinIconFun"})
    public void verifyaboutBtnFun() throws IOException {
        Allure.step("Start Test: Verify About Button Opens SauceLabs Website");
        new LoginPage(driver).navigateTo()
                .enterUserName(DataUtil.getJsonData("testdata","login credintial","username"))
                .enterPassword(DataUtil.getJsonData("testdata","login credintial","password")).logBtn();
        Assert.assertTrue(new LoginPage(driver).successfulLogin());

        Allure.step("Clicking on About button from the menu");
        new HomePage(driver).clickonMenuButton().clickonAboutButton();
        Assert.assertEquals(driver.getCurrentUrl(),"https://saucelabs.com/");
        LogsUtil.info("a new page is opened (about Page) ");
    }


    @BeforeMethod
    public void set(Method method, ITestResult result) {
        DriverFactory driverFactory= new DriverFactory();
        driver=driverFactory.setDriver("edge");

        result.setAttribute("driver", driver);


    }


    @AfterMethod
    public void close() {
        driver.quit();
    }

}
