package com.example.sku_scanner.helpers;

import android.widget.Toast;

public class Toaster {

    public static void shorter(String text) {
        Toast.makeText(App.context, text, Toast.LENGTH_SHORT).show();
    }

    public static void longer(String text) {
        Toast.makeText(App.context, text, Toast.LENGTH_LONG).show();
    }
}
