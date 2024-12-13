package com.saucedemo.page_object;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Header {

    public Header(WebDriver driver) {
        PageFactory.initElements(driver,this);
    }

    @FindBy(className = "shopping_cart_link") // (how = How.CLASS_NAME, using = "shopping_cart_link")
    private WebElement shoppingCartLink;
    @FindBy(className = "shopping_cart_badge")
    private WebElement shoppingCartItemQuantity;

    public String getShoppingCartItemQuantity() {
        return shoppingCartItemQuantity.getText();
    }

    public void clickShoppingCartLink() {
        shoppingCartLink.click();
    }


}
