package com.cnam.eatudiant.api;

import android.content.Context;
import com.cnam.eatudiant.utils.HttpClient;
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class RxApiClient {

    private static RxApiClient instance;

    private Retrofit retrofit;

    private RxApiClient(Context context) {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(MoshiConverterFactory.create())
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
}
