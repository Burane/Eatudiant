package com.cnam.eatudiant.utils;

import android.content.Context;
import android.util.Log;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import org.jetbrains.annotations.NotNull;

public abstract class HttpClient {

    public static OkHttpClient getHttpClient(Context context) {
        SessionManager sessionManager = new SessionManager(context);


        return new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor(s -> Log.d("OkHttp", s)).setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(chain -> {
                    Request.Builder builder = chain.request().newBuilder();

                    String token = sessionManager.fetchAuthToken();

                    if (token != null && !token.isEmpty()) {
                        builder.addHeader("Authorization", "Bearer " + token);
                    }
                    return chain.proceed(builder.build());
                })
                .build();
    }

}
