package com.example.sku_scanner.helpers;

import android.app.Activity;
import android.content.Context;
import android.support.v4.os.ConfigurationCompat;
import android.widget.Toast;

public class ErrorHandling {

    private Context context;
    ToastFactory toastFactory;

    public ErrorHandling(Context context) {
        this.context = context;
        toastFactory = new ToastFactory();
    }

    public void getErrorCode(int errorCode) {
        String language = ConfigurationCompat.getLocales(context.getResources().getConfiguration()).get(0).getLanguage();
        if (errorCode == 0) {
            ((Activity) context).runOnUiThread(new Runnable() {
                public void run() {
                    if (language.equals("en")) {
                        Toast.makeText(context, "failed connection to server", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(context, "عدم ارتباط با سرور", Toast.LENGTH_LONG).show();
                    }
                }
            });
        } else if (errorCode == 422) {

            ((Activity) context).runOnUiThread(new Runnable() {
                public void run() {

//                    toastFactory.createToast(R.string.incorrect_mobilephone,context);
                }
            });

        } else if (errorCode == 201) {
            ((Activity) context).runOnUiThread(new Runnable() {
                public void run() {
//                    toastFactory.createToast(R.string.text_otp_wrong, context);
                }
            });
        }
    }
}
