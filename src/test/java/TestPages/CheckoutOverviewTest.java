package TestPages;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import swaglabs.driver.DriverFactory;
import swaglabs.pages.*;
import swaglabs.utils.DataUtil;
import swaglabs.utils.ScreenShots;

public class CheckoutOverviewTest {
    WebDriver driver;
    SoftAssert softAssert = new SoftAssert();


    @Test
    public void cancelOrderAfterCheckout() {
        new CheckoutOverviewPage(driver).clickOnCancelBtn();
        Assert.assertEquals(new HomePage(driver).verifyingHomePage(),"Products");
    }

    @Test
    public void finishCheckoutProcess() {
        new CheckoutOverviewPage(driver).clickOnFinishBtn();
        Assert.assertEquals(new CheckoutOverviewPage(driver).verifyCompleteOrder(),"Thank you for your order!");
    }


    @BeforeMethod
    public void set(){

        DriverFactory driverFactory = new DriverFactory();
        driver = driverFactory.setDriver("edge");
        new LoginPage(driver).navigateTo()
                .enterUserName(DataUtil.getJsonData("testdata","login credintial","username"))
                .enterPassword(DataUtil.getJsonData("testdata","login credintial","password")).logBtn();
        Assert.assertTrue(new LoginPage(driver).successfulLogin());
        new HomePage(driver).addToCard(DataUtil.getJsonData("testdata","product items","item1","product name"))
                .addToCard(DataUtil.getJsonData("testdata","product items","item3","product name")).clickonCartButton();
        softAssert.assertEquals(new HomePage(driver).getNumberOfProductInCart(),"2");
        softAssert.assertEquals(new CartPage(driver).verifyingCartPage(),"Your Cart");
        new CartPage(driver).cliclOnCheckoutBtn();
        new CheckoutInfoPage(driver).enterFirstName(DataUtil.getJsonData("testdata","checkoutdata","data1","firstname"))
                .enterLastName(DataUtil.getJsonData("testdata","checkoutdata","data1","lastname"))
                .enterPostalCode(DataUtil.getJsonData("testdata","checkoutdata","data1","postalcode")).clickOnContinueCheckoutBtn();



    }

    @AfterMethod
    public void close() {
        driver.quit();
    }
}
