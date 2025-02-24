package com.saucedemo.page_object.util;

public class Helper {

    public static double convertStringWithDollarToDouble(String amount) {
        return Double.parseDouble(amount.replace("$", " "));
    }


}
