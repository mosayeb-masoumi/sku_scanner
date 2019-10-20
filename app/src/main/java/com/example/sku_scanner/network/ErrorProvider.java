package com.example.sku_scanner.network;

import com.example.sku_scanner.helpers.App;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ErrorProvider {
    public static Retrofit.Builder builder =new Retrofit.Builder()
            .baseUrl(App.ServerURL)
            .addConverterFactory(GsonConverterFactory.create());

    public static Retrofit retrofit = builder.build();
}
