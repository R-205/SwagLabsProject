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

import java.io.IOException;

public class CheckoutInfoTest {
    WebDriver driver;
    SoftAssert softAssert = new SoftAssert();


    @Test
    public void verifyCheckoutFormWithoutFname () throws IOException {
        new CheckoutInfoPage(driver).enterLastName(DataUtil.getJsonData("testdata","checkoutdata","data1","lastname"))
                .enterPostalCode(DataUtil.getJsonData("testdata","checkoutdata","data1","postalcode")).clickOnContinueCheckoutBtn();
        Assert.assertTrue(new CheckoutInfoPage(driver).getErrorMessage().contains("First Name is required"));

    }

    @Test
    public void verifyCheckoutFormWithoutLname() {
        new CheckoutInfoPage(driver).enterFirstName(DataUtil.getJsonData("testdata","checkoutdata","data1","firstname"))
                .enterPostalCode(DataUtil.getJsonData("testdata","checkoutdata","data1","postalcode")).clickOnContinueCheckoutBtn();
        Assert.assertTrue(new CheckoutInfoPage(driver).getErrorMessage().contains("Last Name is required"));

    }

    @Test
    public void verifyCheckoutFormWithoutPostalCode() {
         new CheckoutInfoPage(driver).enterFirstName(DataUtil.getJsonData("testdata","checkoutdata","data1","firstname"))
                .enterLastName(DataUtil.getJsonData("testdata","checkoutdata","data1","lastname")).clickOnContinueCheckoutBtn();
        Assert.assertTrue(new CheckoutInfoPage(driver).getErrorMessage().contains("Postal Code is required"));

    }

    @Test
    public void verifyCheckoutFormWithoutFullData() {
        new CheckoutInfoPage(driver).enterFirstName(DataUtil.getJsonData("testdata","checkoutdata","data1","firstname"))
                .enterLastName(DataUtil.getJsonData("testdata","checkoutdata","data1","lastname"))
                .enterPostalCode(DataUtil.getJsonData("testdata","checkoutdata","data1","postalcode")).clickOnContinueCheckoutBtn();
        Assert.assertEquals(new CheckoutOverviewPage(driver).verifyingCheckOutPage(),"Checkout: Overview");

    }

@Test
    public void verifyCancelCheckoutAfterEnterData() {
        new CheckoutInfoPage(driver).enterFirstName(DataUtil.getJsonData("testdata","checkoutdata","data1","firstname"))
                .enterLastName(DataUtil.getJsonData("testdata","checkoutdata","data1","lastname"))
                .enterPostalCode(DataUtil.getJsonData("testdata","checkoutdata","data1","postalcode")).clickOnCancelCheckoutBtn();
        Assert.assertEquals(new CartPage(driver).verifyingCartPage(), "Your Cart");
    }




    @BeforeMethod
    public void set() {

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


    }

    @AfterMethod
    public void close() {
        driver.quit();
    }
}
