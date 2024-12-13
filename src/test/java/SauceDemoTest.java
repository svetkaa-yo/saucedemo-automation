import com.saucedemo.page_object.*;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SauceDemoTest {

    WebDriver driver;//объявлена пустая переменная класса
    LoginPage loginPage;
    InventoryPage inventoryPage;
    CartPage cartPage;
    Header header;


    Configurations configs; //массив разных конфигураций, переменная класса config.properties
    Configuration config; //класс для работы с одной конфигурацией

    @BeforeMethod
    public void setUp() throws ConfigurationException {
        driver = new ChromeDriver(); //придаем значение
        loginPage = new LoginPage(driver);
        inventoryPage = new InventoryPage(driver);
        cartPage = new CartPage(driver);
        header = new Header(driver);

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
        assertThat(inventoryItemCount).isEqualTo(6); //сравниваем используя AssertJ

        InventoryItem item = inventoryPage.findItemByName("Backpack");//найти нужный item из списка
        assertThat(item).withFailMessage("Item not found").isNotNull();//проверить, что объект не пустой
        //проверить текст и цвет кнопки
        assertThat(item.getButtonText()).isEqualTo("Add to cart");
        assertThat(item.getButtonColor()).isEqualTo("rgba(19, 35, 34, 1)"); //selenium переводит в rgba
        //добавить item в корзину
        item.clickButton();
        //сравнить поменялся ли цвет и текст
        assertThat(item.getButtonText()).isEqualTo("Remove");
        assertThat(item.getButtonColor()).isEqualTo("rgba(226, 35, 26, 1)");
        //проверить, что на иконке корзины появилась цифра 1
        assertThat(header.getShoppingCartItemQuantity()).isEqualTo("1");
        //нажать на иконку корзины
        header.clickShoppingCartLink();
        //убедиться, что открыта страница корзины
        assertThat(driver.getCurrentUrl()).isEqualTo( "https://www.saucedemo.com/cart.html"); //нужна ли эта проверка, погуглить лучшую проверку для ссылки
        //проверить цифру на иконке корзины
        Assert.assertEquals(header.getShoppingCartItemQuantity(),"1");
        //посчитать сколько в корзине items и проверить, что только один
        Integer cartItemCount = cartPage.cartItemCount();
        assertThat(cartItemCount).isEqualTo(1);
        //достать первый item
        CartItem cartItem = cartPage.getFirstCartItem();
        //Проверить имя и количество товара
        assertThat(cartItem.getItemName()).isEqualTo("Sauce Labs Backpack");
        assertThat(cartItem.getItemQuantity()).isEqualTo("1");














    }

    @AfterMethod
    public void tearDown() {
        driver.close(); //закрыть вкладку
        driver.quit();  //закроется программа-драйвер и мы ее не сможем больше использовать
    }

}

