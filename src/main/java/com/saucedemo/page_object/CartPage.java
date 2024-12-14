package com.saucedemo.page_object;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static org.openqa.selenium.support.PageFactory.initElements;

public class CartPage {

    @FindBy(className = "cart_item")
    private List<WebElement> cartItemElements;
    @FindBy(id = "checkout")
    private WebElement checkoutButton;


    public CartPage(WebDriver driver) {
        initElements(driver, this);
    }

    public Integer cartItemCount() {
        return cartItemElements.size();
    }

    public CartItem getFirstCartItem() {
        WebElement firstWebElement = cartItemElements.get(0);
        return new CartItem(firstWebElement);
    }

    public CartItem getCartItemByName(String itemName) {
        for (WebElement cartItemByNameElement : cartItemElements) { //итерируем по списку и переменная куда будет присваиваться элементы из списка
            if (cartItemByNameElement.getText().contains(itemName)) {
                return new CartItem(cartItemByNameElement);
            }
        }
        return null; //если не нашел, то возвращает null
    }

    public void clickCheckoutButton(){
        checkoutButton.click();
    }




}
