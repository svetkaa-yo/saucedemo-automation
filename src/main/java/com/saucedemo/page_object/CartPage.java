package com.saucedemo.page_object;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static org.openqa.selenium.support.PageFactory.initElements;

public class CartPage {

    @FindBy(className = "shopping_cart_badge")
    private WebElement shoppingCartItemQuantity;
    @FindBy(className = "cart_item")
    private List<WebElement> cartItemElements;


    public CartPage(WebDriver driver) {
        initElements(driver, this);
    }

    public String getShoppingCartItemQuantity() {
        return shoppingCartItemQuantity.getText();
    }

    public CartItem findCartItemByName(String name) {
        for (WebElement webElement : cartItemElements) {
            if (webElement.getText().startsWith(name)) {
                return new CartItem(webElement);
            }
        }
        return null;
    }

    public Integer getCartItemCount() {
        return cartItemElements.size();
    }

    public CartItem findFirstCartItem() {
        WebElement firstWebElement = cartItemElements.get(0);
        return firstWebElement.findElement(By.)
    }
        return null;


}
