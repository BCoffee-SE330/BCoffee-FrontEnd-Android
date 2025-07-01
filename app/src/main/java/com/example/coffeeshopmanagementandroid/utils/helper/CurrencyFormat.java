package com.example.coffeeshopmanagementandroid.utils.helper;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyFormat {
    public static String formatVND(BigDecimal amount) {
        if (amount == null) return "0 VND";
        NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
        return formatter.format(amount) + " VND";
    }
}
