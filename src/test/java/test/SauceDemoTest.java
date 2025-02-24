package test;

import com.github.javafaker.Faker;
import com.saucedemo.page_object.*;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.saucedemo.page_object.CartItem.Helper.convertStringWithDollarToDouble;
import static org.assertj.core.api.Assertions.assertThat;

public class SauceDemoTest {

    WebDriver driver;//empty class variable declared
    LoginPage loginPage;
    InventoryPage inventoryPage;
    CartPage cartPage;
    Header header;
    CheckoutPage checkoutPage;


    Configurations configs; //array of different configurations, class variable config.properties
    Configuration config; //class for working with one configuration

    Faker randomData = new Faker();


    @BeforeMethod
    public void setUp() throws ConfigurationException {
        driver = new ChromeDriver(); //assign a value to a variable
        loginPage = new LoginPage(driver);
        inventoryPage = new InventoryPage(driver);
        cartPage = new CartPage(driver);
        header = new Header(driver);
        checkoutPage = new CheckoutPage(driver);

        configs = new Configurations();
        config = configs.properties("config.properties");

        driver.get(config.getString("web.url")); //open the website
    }

    @Test //TODO: test case (1) open login page (2) user writes username (3) user writes password (4) user click on login button,(5) expected result
    public void openSauceDemoLoginTest() {
        loginPage.authorize(config.getString("username"),config.getString("password"));
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/inventory.html");//JUnit, test actual result, expected result
    }
    @Test //TODO: Implement add item to the cart
    public void sauceDemoAddItemToTheCartTest() {
        loginPage.authorize(config.getString("username"), config.getString("password"));

        Integer inventoryItemCount = inventoryPage.getInventoryItemCount(); //counting how much items
        assertThat(inventoryItemCount).isEqualTo(6); //AssertJ

        InventoryItem item = inventoryPage.findItemByName("Backpack");//find the desired item from the list
        InventoryItem item2 = inventoryPage.findItemByName("Bike Light");//find the desired item from the list
        assertThat(item).withFailMessage("Item not found").isNotNull();//check if object is not empty, if empty, then returnes message
        assertThat(item2).withFailMessage("Item not found").isNotNull();
        //check the button text and color
        assertThat(item.getButtonText()).isEqualTo("Add to cart");
        assertThat(item.getButtonColor()).isEqualTo("rgba(19, 35, 34, 1)"); //selenium def.format is rgba
        //add item to the cart
        item.clickButton();
        item2.clickButton();
        //assert color and text changes
        assertThat(item.getButtonText()).isEqualTo("Remove");
        assertThat(item.getButtonColor()).isEqualTo("rgba(226, 35, 26, 1)");
        //check that the number of items appears on the cart icon
        assertThat(header.getShoppingCartItemQuantity()).isEqualTo("2");
        //click on the cart icon
        header.clickShoppingCartLink();
        //assert that the cart page is opened
        assertThat(driver.getCurrentUrl()).isEqualTo( "https://www.saucedemo.com/cart.html"); 
        //check the number of items on the cart icon
        Assert.assertEquals(header.getShoppingCartItemQuantity(),"2");
        //count items and check number
        Integer cartItemCount = cartPage.cartItemCount();
        assertThat(cartItemCount).isEqualTo(2);
        //get first item and item by name
        CartItem cartItem = cartPage.getFirstCartItem();
        CartItem cartItem2 = cartPage.getCartItemByName("Bike Light");
        //check item name and quantity
        assertThat(cartItem.getItemName()).isEqualTo("Sauce Labs Backpack");
        assertThat(cartItem2.getItemName()).isEqualTo("Sauce Labs Bike Light");
        //the quantity of the first item must always be 1 initially
        assertThat(cartItem.getItemQuantity()).isEqualTo("1");
        //click Checkout button
        cartPage.clickCheckoutButton();
        //fill out the form and click Continue button
        checkoutPage.fillStepOne(
                randomData.funnyName().name(),
                randomData.address().lastName(),
                randomData.address().zipCode());
        //Find price by product name as String, convert to Double
        double backpackPrice = convertStringWithDollarToDouble(checkoutPage.getPriceByItemName("Backpack"));
        double bikeLightPrice = convertStringWithDollarToDouble(checkoutPage.getPriceByItemName("Bike Light"));
        double sumPrice = backpackPrice + bikeLightPrice;
        //sum+tax compare with totalPrice
        double totalPrice = sumPrice + checkoutPage.getTax();
        assertThat(checkoutPage.getSummaryTotal()).isEqualTo(totalPrice);
        //click Finish button and compare text
        checkoutPage.getFinishButton().click();
        assertThat(checkoutPage.getCompleteHeader().getText()).isEqualTo("Thank you for your order!");
    }

    @Test //TODO: User Login validation
    public void sauceDemoUserLoginValidationTest(){
        //Steps: set username, set password, click button, get error message, clear fields
        //Test Cases: empty fields, one field empty, one field invalid data, both invalid

        loginPage.authorize("","");
        assertThat(loginPage.getErrorMessage()).isEqualTo("Epic sadface: Username is required");
        loginPage.clearFields();

        loginPage.authorize("standard_user","");
        assertThat(loginPage.getErrorMessage()).isEqualTo("Epic sadface: Password is required");
        loginPage.clearFields();

        loginPage.authorize("","secret_sauce");
        assertThat(loginPage.getErrorMessage()).isEqualTo("Epic sadface: Username is required");
        loginPage.clearFields();

        loginPage.authorize("standard_user","secret");
        assertThat(loginPage.getErrorMessage()).isEqualTo
                ("Epic sadface: Username and password do not match any user in this service");
        loginPage.clearFields();

        loginPage.authorize("standard","secret_sauce");
        assertThat(loginPage.getErrorMessage()).isEqualTo
                ("Epic sadface: Username and password do not match any user in this service");
        loginPage.clearFields();

        loginPage.authorize("standard","secret");
        assertThat(loginPage.getErrorMessage()).isEqualTo
                ("Epic sadface: Username and password do not match any user in this service");
        loginPage.clearFields();

    }

    @AfterMethod
    public void tearDown() {
        driver.close(); //close tab
        driver.quit();  //close the driver program
    }

}

