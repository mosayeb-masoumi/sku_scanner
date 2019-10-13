package com.example.sku_scanner.helpers;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;
import android.widget.Toast;

public class ToastFactory {
    public void createToast(String message, Context context){

        Typeface typeface = Typeface.createFromAsset(context.getAssets(),"fonts/BYekan.ttf");
//        Typeface typeface = Typeface.createFromAsset(context.getAssets(),"fonts/Vazir-Medium.ttf");
        Toast toast = Toast.makeText(context,message,Toast.LENGTH_LONG);
        TextView textView = toast.getView().findViewById(android.R.id.message);
        textView.setTypeface(typeface);
        toast.show();
    }

    public void createToast(int message,Context context){

        if (message==0)
            return;

        Typeface typeface = Typeface.createFromAsset(context.getAssets(),"fonts/BYekan.ttf");
//        Typeface typeface = Typeface.createFromAsset(context.getAssets(),"fonts/Vazir-Medium.ttf");
        Toast toast = Toast.makeText(context,message,Toast.LENGTH_LONG);
        TextView textView = toast.getView().findViewById(android.R.id.message);
        textView.setTypeface(typeface);
        toast.show();
    }
}
