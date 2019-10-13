package com.example.sku_scanner.network;

import android.content.Context;
import android.content.Intent;

import com.example.sku_scanner.helpers.App;
import com.example.sku_scanner.helpers.Cache;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Authenticator;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceProvider {
    private Service mService;

    public ServiceProvider(Context context) {

        //config client and retrofit:

        OkHttpClient.Builder clientBuilder = new OkHttpClient().newBuilder();
        clientBuilder.connectTimeout(3, TimeUnit.MINUTES)
                .readTimeout(3, TimeUnit.MINUTES);
        clientBuilder.cache(null);


        if (!Cache.getString("email").equals("") &&  !Cache.getString("password").equals("")) {

//            clientBuilder.addInterceptor(chain -> {
//                Request request = chain.request().newBuilder()
//                        .addHeader("lang", storage.retriveLanguage())
//                        .addHeader("token", PreferenceStorage.getInstance(context).retriveToken())
//                        .build();
//                return chain.proceed(request);
//            });
        }






        Retrofit retrofit = new Retrofit.Builder().baseUrl(App.ServerURL).client(clientBuilder.build()).
                addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();

        mService = retrofit.create(Service.class);
    }

    public Service getmService() {
        return mService;
    }
}
