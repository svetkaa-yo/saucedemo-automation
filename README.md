## Overview

I have created several automated test cases for the https://www.saucedemo.com/ demo website. 
It features a simple e-commerce-style interface that allows users to simulate logging in, adding products to a shopping cart, and completing transactions. 

## The Structure 

### 1. **Automated tests (`test/java/test/`)**
SauceDemoTest.java
- openSauceDemoLoginTest() - happy-path Login test
- sauceDemoUserLoginValidationTest() - User Login validation test
- sauceDemoAddItemToTheCartTest() - User scenario for adding an item to the cart and tests to verify the expected result.

### 2. **Page Object (`main/java/com/saucedemo/page_object/`)**
- LoginPage.java
- InventoryPage.java
- InventoryItem.java
- Header.java
- CartPage.java
- CartItem.java
- CheckoutPage.java

### 3. **Utils (`main/java/com/saucedemo/page_object/util/`)**
- Helper.java 

### 4. **Test configurations (`test/java/resources/config/`)**
- config.properties
