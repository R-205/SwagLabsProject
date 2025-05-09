package TestPages;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;
import swaglabs.driver.DriverFactory;
import swaglabs.pages.CartPage;
import swaglabs.pages.HomePage;
import swaglabs.pages.LoginPage;
import swaglabs.utils.DataUtil;

import java.io.IOException;

public class CartTest  {
    WebDriver driver;
    SoftAssert softAssert =new SoftAssert();
    @Test
    public void checkoutProduct() {
        new HomePage(driver).addToCard(DataUtil.getJsonData("testdata", "product items", "item1", "product name")).clickonCartButton();
        softAssert.assertEquals(new HomePage(driver).getNumberOfProductInCart(), "1");
        softAssert.assertEquals(new CartPage(driver).verifyingCartPage(), "Your Cart");
        new CartPage(driver).cliclOnCheckoutBtn();
    }

    @Test
    public void checkContinueShopping() throws IOException {

          new HomePage(driver).addToCard(DataUtil.getJsonData("testdata","product items","item1","product name")).clickonCartButton();
        softAssert.assertEquals(new HomePage(driver).getNumberOfProductInCart(),"1");
        new CartPage(driver).cliclOncountinueShoppingBtn();
        softAssert.assertTrue(new HomePage(driver).verifyHomePage());
        new HomePage(driver).addToCard(DataUtil.getJsonData("testdata","product items","item2","product name")).clickonCartButton();
        softAssert.assertEquals(new HomePage(driver).getNumberOfProductInCart(),"2");

    }

    @BeforeMethod
    public void set() {

        DriverFactory driverFactory = new DriverFactory();
        driver = driverFactory.setDriver("edge");
        new LoginPage(driver).navigateTo()
                .enterUserName(DataUtil.getJsonData("testdata","login credintial","username"))
                .enterPassword(DataUtil.getJsonData("testdata","login credintial","password")).logBtn();
        Assert.assertTrue(new LoginPage(driver).successfulLogin());


    }

    @AfterMethod
    public void close() {
        driver.quit();
    }
}
