package com.example.sku_scanner.network;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import com.example.sku_scanner.helpers.App;
import com.example.sku_scanner.helpers.Cache;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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


        if (!Cache.getString("email").equals("") && !Cache.getString("password").equals("")) {
            clientBuilder.addInterceptor(chain -> {
                Request request = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + Cache.getString("email"))
                        .addHeader("Accept", "application/json")
                        .build();
                return chain.proceed(request);
            });
        }else{
            // for error handling in login request
            clientBuilder.addInterceptor(chain -> {
                Request request = chain.request().newBuilder()
                        .addHeader("Accept", "application/json")
                        .build();
                return chain.proceed(request);
            });
        }


        //error handlong
        clientBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Response response = chain.proceed(request);

//                int a = response.code();
                return response;
            }
        }).build();





//        Gson gson = new GsonBuilder()
//                .setLenient()
//                .create();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(App.ServerURL).client(clientBuilder.build()).
                addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build();

        mService = retrofit.create(Service.class);
    }

    public Service getmService() {
        return mService;
    }
}
