package com.cnam.eatudiant.api;

import android.content.Context;
import com.cnam.eatudiant.Config;
import com.cnam.eatudiant.jsonAdapter.CustomDateAdapter;
import com.cnam.eatudiant.utils.HttpClient;
import com.squareup.moshi.Moshi;
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

import java.util.Date;

public class RxApiClient {

    private static RxApiClient instance;

    private Retrofit retrofit;

    private RxApiClient(Context context) {

        Moshi moshi = new Moshi.Builder()
                .add(Date.class, new CustomDateAdapter())
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(Config.API_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(RxJava3CallAdapterFactory.createAsync())
                .client(HttpClient.getHttpClient(context))
                .build();
    }

    public static RxUserApiService getRxApiService(Context context) {
        if (instance == null) {
            instance = new RxApiClient(context);
        }
        return instance.retrofit.create(RxUserApiService.class);
    }

    public static RxRecipesApiService getRxRecipesApiService(Context context) {
        if (instance == null) {
            instance = new RxApiClient(context);
        }
        return instance.retrofit.create(RxRecipesApiService.class);
    }
}
