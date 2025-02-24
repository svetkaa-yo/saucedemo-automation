package com.saucedemo.page_object;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.openqa.selenium.support.PageFactory.*;

public class LoginPage {

    WebDriverWait wait;


    //PageFactory
    @FindBy(id="user-name") //annotations so that the driver understands what element it is in HTML
    private WebElement usernameField; //
    @FindBy(name="password")
    private WebElement passwordField;
    @FindBy(xpath="//input[@data-test='login-button']")
    private WebElement loginButton;
    @FindBy(className="error-message-container")
    private WebElement errorMessageElement;


    public LoginPage(WebDriver driver) {
        //this.usernameField = driver.findElement(By.id("user-name")); same as init
        //An example of reflection, the ability of program to analyze and modify its own composition and behavior at run time.
        initElements(driver,this); //Selenium method, scans this class (three fields), sees annotations and assigns a value to each of them using findby
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(2));
    }
    public void authorize(String username,String password){
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        loginButton.click();
    }

    public String getErrorMessage(){
        return errorMessageElement.getText();
    }

    public void clearFields(){
        usernameField.sendKeys(Keys.CONTROL+"a");
        usernameField.sendKeys(Keys.DELETE);
        passwordField.sendKeys(Keys.CONTROL+"a");
        passwordField.sendKeys(Keys.DELETE);
    }

}
