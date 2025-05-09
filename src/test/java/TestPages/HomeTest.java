package TestPages;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import swaglabs.driver.DriverFactory;
import swaglabs.pages.HomePage;
import swaglabs.pages.LoginPage;
import swaglabs.utils.DataUtil;

import java.util.List;

public class  HomeTest  {

    WebDriver driver;

    @Test
    public void selectOrderOfProductsByPriceLowToHigh(){
        HomePage home=new HomePage(driver);
        home.selectOptionFromDropdown(DataUtil.getJsonData("testdata","orderofData","order1"));
        List<String> productNames = home.getAllProductNames();
        // تأكد من الطباعة عشان تشوفي الأسامي
        System.out.println(" product names :");
        for (String name : productNames) {
            System.out.println(name);
        }

//        String firstProductName = productNames.get(0);
        Assert.assertEquals(productNames.get(0),"Sauce Labs Onesie");
    }

    @Test
    public void selectOrderOfProductsByPriceHighToLow(){
        HomePage home=new HomePage(driver);
        home.selectOptionFromDropdown(DataUtil.getJsonData("testdata","orderofData","order2"));
        List<String> productNames = home.getAllProductNames();
        System.out.println(" product names :");
        for (String name : productNames) {
            System.out.println(name);
        }
//        String firstProductName = productNames.get(0);
        Assert.assertEquals(productNames.get(0),"Sauce Labs Fleece Jacket");
//        Assert.assertEquals(productNames.get(5),"Sauce Labs Onesie");



    }

    @Test
    public void selectOrderOfProductsByName_A_To_Z(){
        HomePage home=new HomePage(driver);
        home.selectOptionFromDropdown(DataUtil.getJsonData("testdata","orderofData","order3"));
        List<String> productNames = home.getAllProductNames();
        // تأكد من الطباعة عشان تشوفي الأسامي
        System.out.println(" product names :");
        for (String name : productNames) {
            System.out.println(name);
        }
//        String firstProductName = productNames.get(0);
        Assert.assertEquals( productNames.get(0),"Sauce Labs Backpack");
    }

    @Test
    public void selectOrderOfProductsByName_Z_To_A(){
        HomePage home=new HomePage(driver);

        home.selectOptionFromDropdown(DataUtil.getJsonData("testdata","orderofData","order4"));

        List<String> productNames = home.getAllProductNames();

        // تأكد من الطباعة عشان تشوفي الأسامي
        System.out.println(" product names :");
        for (String name : productNames) {
            System.out.println(name);
        }
//        String firstProductName = productNames.get(0);
        Assert.assertEquals(productNames.get(0),"Test.allTheThings() T-Shirt (Red)");

    }


@Test
public void verifyFacebookIconFun() {

    HomePage home = new HomePage(driver);

        // 2. حفظ معرف النافذة الأصلية قبل النقر على الأيقونة
        String originalWindow = driver.getWindowHandle();

        // 3. النقر على الأيقونة لفتح صفحة الفيسبوك في تبويب جديد
        home.clickonFacebookIcon();

        // 4. استدعاء الدالة للتحقق من النافذة الجديدة
        String newWindow = home.getNewWindowHandle(driver, originalWindow);

        // التحقق من أن هناك نافذة جديدة
        if (newWindow != null) {
            driver.switchTo().window(newWindow);

            // 5. التحقق من أن الصفحة الجديدة هي صفحة فيسبوك
            Assert.assertTrue(driver.getCurrentUrl().contains("facebook"), "The Facebook page did not open in a new tab.");
        } else {
            Assert.fail("No new window was opened.");
        }

        // 6. العودة إلى النافذة الأصلية دون إغلاقها
        driver.switchTo().window(originalWindow);
    }
@Test
    public void verifyLinkedinIconFun() {

        HomePage home = new HomePage(driver);
        String originalWindow = driver.getWindowHandle();
        home.clickonLinkedinIcon();
        String newWindow = home.getNewWindowHandle(driver, originalWindow);
        if (newWindow != null) {
            driver.switchTo().window(newWindow);
            Assert.assertTrue(driver.getCurrentUrl().contains("https://www.linkedin.com/company/sauce-labs/"), "The linkedin page did not open in a new tab.");
        } else {
            Assert.fail("No new window was opened.");
        }
        driver.switchTo().window(originalWindow);
    }

@Test
    public void verifyTwitterIconFun() {

        HomePage home = new HomePage(driver);
        String originalWindow = driver.getWindowHandle();
        home.clickonTwitterIcon();
        String newWindow = home.getNewWindowHandle(driver, originalWindow);
        if (newWindow != null) {
            driver.switchTo().window(newWindow);
            Assert.assertTrue(driver.getCurrentUrl().contains("x"), "The x page did not open in a new tab.");
        } else {
            Assert.fail("No new window was opened.");
        }
        driver.switchTo().window(originalWindow);
    }

        @Test
    public void addProduct(){
        new HomePage(driver).addToCard(DataUtil.getJsonData("testdata","product items","item1","product name"));
            Assert.assertEquals( new HomePage(driver).getNumberOfProductInCart(),"1");

        }
//
//
    @Test(dependsOnMethods = {"verifyFacebookIconFun","verifyTwitterIconFun","verifyLinkedinIconFun"})
    public void verifyAllItemsBtnFun(){
        new HomePage(driver).clickonMenuButton().clickonallitemsButton();
        Assert.assertEquals(new HomePage(driver).getAllProductNames().size(),6);


    }


    @Test(dependsOnMethods = {"verifyFacebookIconFun","verifyTwitterIconFun","verifyLinkedinIconFun"})
    public void verifyaboutBtnFun(){
        new HomePage(driver).clickonMenuButton().clickonAboutButton();
        Assert.assertEquals(driver.getCurrentUrl(),"https://saucelabs.com/");
        driver.navigate().back();


    }

@Test
public void verifyresetBtnFun(){
        new HomePage(driver).addToCard(DataUtil.getJsonData("testdata","product items","item1","product name"));
        Assert.assertEquals( new HomePage(driver).getNumberOfProductInCart(),"1");
        new HomePage(driver).clickonMenuButton().clickonResetAppStateButton().closeMenu();
        Assert.assertEquals( new HomePage(driver).getNumberOfProductInCart(),"0");
    }

































//    @Test
//    public void testPrintProductNames() {
//        HomePage home=new HomePage(driver);
//        List<String> productNames = home.getAllProductNames();
//
//        // تأكد من الطباعة عشان تشوفي الأسامي
//        System.out.println("أسامي المنتجات:");
//        for (String name : productNames) {
//            System.out.println(name);
//        }
//    }



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
