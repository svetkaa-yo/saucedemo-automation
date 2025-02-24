package com.saucedemo.page_object;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

import static org.openqa.selenium.support.PageFactory.initElements;

public class InventoryPage {
    @FindBy(className = "inventory_item")
    private List<WebElement> inventoryItemElements;

    public InventoryPage(WebDriver driver) { //create Constructor and PageFactory
        initElements(driver, this);
    }

    public InventoryItem findItemByName(String name) {
        for (WebElement webElement : inventoryItemElements) { //iterate over the inventoryItemElements list and the variable where the elements from the list will be assigned
            if (webElement.getText().contains(name)) {
                return new InventoryItem(webElement);
            }
        }
        return null; //if not found, then returns null
    }

    public Integer getInventoryItemCount() {
        return inventoryItemElements.size();
    }



}
