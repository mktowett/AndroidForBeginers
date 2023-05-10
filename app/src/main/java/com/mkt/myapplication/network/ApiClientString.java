package com.mkt.myapplication.network;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class ApiClientString {
    public static final String BASE_URL = "http://www.thecocktaildb.com/api/json/v1/1/";

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        int KEY_TIMEOUT = 120;
        builder.readTimeout(KEY_TIMEOUT, TimeUnit.SECONDS).connectTimeout(KEY_TIMEOUT, TimeUnit.SECONDS).writeTimeout(KEY_TIMEOUT, TimeUnit.SECONDS);

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.networkInterceptors().add(httpLoggingInterceptor);

        OkHttpClient okHttpClient = builder.build();
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
        }

        return retrofit;
    }

}