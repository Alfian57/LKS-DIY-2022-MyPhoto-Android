package com.postingan.my_photo.Api;

import android.content.Context;

import com.postingan.my_photo.R;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiConfig {
    private static Retrofit retrofit = null;

    public static Retrofit getRetrofit(Context context){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(loggingInterceptor);

        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(context.getResources().getString(R.string.api_url))
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(builder.build())
                    .build();
        }
        return retrofit;
    }
}
