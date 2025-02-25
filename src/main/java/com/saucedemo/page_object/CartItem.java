package com.saucedemo.page_object;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CartItem {
    @FindBy (className = "cart_quantity")
    private WebElement itemIndex;
    @FindBy (className = "inventory_item_name")
    private WebElement itemName;

    public CartItem(WebElement webElement){
        PageFactory.initElements(webElement,this);
    }

    public String getItemQuantity(){
        return itemIndex.getText();
    }

    public String getItemName(){
        return itemName.getText();
    }

}
