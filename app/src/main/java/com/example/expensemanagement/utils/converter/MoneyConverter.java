package com.example.expensemanagement.utils.converter;

import java.text.DecimalFormat;

public class MoneyConverter {
    public static String convertMoneyToString(long money) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
        return decimalFormat.format(money) + "đ";
    }
}
