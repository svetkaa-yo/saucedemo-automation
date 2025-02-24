package com.saucedemo.page_object;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.Optional;

import static com.saucedemo.page_object.util.Helper.convertStringWithDollarToDouble;

public class CheckoutPage {

    private final By itemPriceElement = By.xpath(".//div[@class='inventory_item_price']");

    @FindBy(id = "first-name")
    private WebElement firstNameField;
    @FindBy(id = "last-name")
    private WebElement lastNameField;
    @FindBy(id = "postal-code")
    private WebElement postalCodeField;
    @FindBy(id = "continue")
    private WebElement continueButton;
    @FindBy(className = "cart_item")
    private List<WebElement> cartItemElements;
    @FindBy(className = "summary_tax_label")
    private WebElement taxLabel;
    @FindBy(className = "summary_total_label")
    private WebElement summaryTotalLabel;
    @FindBy(id = "finish")
    private WebElement finishButton;
    @FindBy(className = "complete-header")
    private WebElement completeHeader;

    public CheckoutPage(WebDriver driver){
        PageFactory.initElements(driver,this);
    }


    public WebElement getTaxLabel() {
        return taxLabel;
    }

    public WebElement getSummaryTotalLabel() {
        return summaryTotalLabel;
    }

    public WebElement getFinishButton() {
        return finishButton;
    }

    public WebElement getCompleteHeader() {
        return completeHeader;
    }

    public void fillStepOne(String firstName, String lastName, String postalCode){
        firstNameField.sendKeys(firstName);
        lastNameField.sendKeys(lastName);
        postalCodeField.sendKeys(postalCode);
        continueButton.click();
    }

    public String getPriceByItemName(String itemName){
        Optional<WebElement>cartItem = cartItemElements.stream().
                filter(item -> item.getText().contains(itemName)).findFirst();
        return cartItem.get().findElement(itemPriceElement).getText();
    }


    public double getTax() {
        return convertStringWithDollarToDouble(getTaxLabel().getText().replace("Tax: ", ""));
    }

    public double getSummaryTotal() {
        return convertStringWithDollarToDouble(getSummaryTotalLabel().getText().replace("Total: ", ""));
    }



    


}
