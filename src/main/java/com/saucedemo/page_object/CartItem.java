package com.saucedemo.page_object;

import org.openqa.selenium.WebDriver;
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

    public String getItemName(){
        return itemName.getText();
    }

    public String getItemIndex(){
        return itemIndex.getText();
    }



}
