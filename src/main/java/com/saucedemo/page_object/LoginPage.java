package com.saucedemo.page_object;

import org.openqa.selenium.By;
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
    @FindBy(id="user-name") //аннотации чтобы драйвер понял какой это элемент в HTML
    public WebElement usernameField; //
    @FindBy(name="password")
    public WebElement passwordField;
    @FindBy(xpath="//input[@data-test='login-button']")
    public WebElement loginButton;

    public LoginPage(WebDriver driver) {
        //this.usernameField = driver.findElement(By.id("user-name")); тоже самое что инит
        //пример рефлексии - возможность программам анализировать и модифицировать свой собственный состав и поведение во время выполнения.
        initElements(driver,this); //метод Селениум, сканирует класс this (три поля), видит аннотации и каждому из них присваивает значение с помощью findby
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(2));
    }
    public void authorize(String username,String password){
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        loginButton.click();
    }
}
