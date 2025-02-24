package com.saucedemo.page_object;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class InventoryItem { //get description, text, price, action button
    @FindBy(className = "btn_inventory")
    private WebElement buttonElement;

    public InventoryItem(WebElement webElement) { //outer web element - div
        PageFactory.initElements(webElement, this); //each WebElement has parent Driver
    }
    public String getButtonText() {
        return buttonElement.getText();
    }
    public String getButtonColor() {
        return buttonElement.getCssValue("color");
    }
    public void clickButton(){
        buttonElement.click();
    }



}
