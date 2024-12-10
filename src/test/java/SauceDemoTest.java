import com.saucedemo.page_object.CartPage;
import com.saucedemo.page_object.InventoryItem;
import com.saucedemo.page_object.InventoryPage;
import com.saucedemo.page_object.LoginPage;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.http.TextMessage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.w3c.dom.Text;

public class SauceDemoTest {

    WebDriver driver;//объявлена пустая переменная класса
    LoginPage loginPage;
    InventoryPage inventoryPage;
    CartPage cartPage;

    Configurations configs; //массив разных конфигураций, переменная класса config.properties
    Configuration config; //класс для работы с одной конфигурацией

    @BeforeMethod
    public void setUp() throws ConfigurationException {
        driver = new ChromeDriver(); //придаем значение
        loginPage = new LoginPage(driver);
        inventoryPage = new InventoryPage(driver);
        cartPage = new CartPage(driver);

        configs = new Configurations();
        config = configs.properties("config.properties");

        driver.get(config.getString("web.url")); //открыть сайт
    }

    @Test //TODO: test case (1) open login page (2) user writes username (3) user writes password (4) user click on login button,(5) expected result
    public void openSauceDemoLoginTest() {
        loginPage.authorize(config.getString("username"),config.getString("password"));
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html");//проверка actual result, expected result
    }
    @Test //TODO: Implement add item to the cart
    public void sauceDemoAddItemToTheCartTest() {
        loginPage.authorize(config.getString("username"), config.getString("password"));

        Integer inventoryItemCount = inventoryPage.getInventoryItemCount(); //считаем сколько их
        Assert.assertEquals(inventoryItemCount, 6); //сравниваем

        InventoryItem item = inventoryPage.findItemByName("Sauce Labs Backpack");//найти нужный item из списка
        Assert.assertNotNull(item,"Item not found");//проверить, что объект не пустой
        //проверить текст и цвет кнопки
        Assert.assertEquals(item.getButtonText(),"Add to cart");
        Assert.assertEquals(item.getButtonColor(),"rgba(19, 35, 34, 1)"); //selenium переводит в rgba
        //добавить item в корзину
        item.clickButton();
        //сравниваем поменялся ли цвет и текст
        Assert.assertEquals(item.getButtonText(),"Remove");
        Assert.assertEquals(item.getButtonColor(),"rgba(226, 35, 26, 1)");
        //проверить, что на иконке корзины появилась цифра 1
        Assert.assertEquals(inventoryPage.getShoppingCartItemQuantity(),"1");
        //нажать на иконку корзины
        inventoryPage.clickShoppingCartLink();
        //убедиться, что мы находимся на странице корзины
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/cart.html");
        //снова проверяем цифру на иконке корзины
        Assert.assertEquals(cartPage.getShoppingCartItemQuantity(),"1");
        //проверить есть ли в корзине item с таким названием








    }

    @AfterMethod
    public void tearDown() {
        driver.close(); //закрыть вкладку
        driver.quit();  //закроется программа-драйвер и мы ее не сможем больше использовать
    }

}

