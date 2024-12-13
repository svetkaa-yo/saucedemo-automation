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

    public InventoryPage(WebDriver driver) { //создаем конструктор и PageFactory
        initElements(driver, this);
    }

    public InventoryItem findItemByName(String name) {
        for (WebElement webElement : inventoryItemElements) { //итерируем по inventoryItemElements списку и переменная куда будет присваиваться элементы из списка
            if (webElement.getText().contains(name)) {
                return new InventoryItem(webElement);
            }
        }
        return null; //если не нашел, то возвращает null
    }

    public Integer getInventoryItemCount() {
        return inventoryItemElements.size();
    }



}
