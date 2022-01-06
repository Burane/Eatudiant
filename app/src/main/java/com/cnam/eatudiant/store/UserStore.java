package com.cnam.eatudiant.store;

import android.content.Context;
import android.util.Log;
import com.cnam.eatudiant.api.RxApiClient;
import com.cnam.eatudiant.api.RxApiService;
import io.reactivex.rxjava3.functions.Consumer;
import lombok.SneakyThrows;

import java.util.Map;

public class UserStore {

    private Map<String, Consumer> consumer;

    private RxApiService rxApiService;
    private Context context;

    public UserStore(Map<String, Consumer> consumer, Context context) {
        this.consumer = consumer;
    }

    @SneakyThrows
    public void login() {
        Log.i("eatudiant_debug", "login: ");

        rxApiService = RxApiClient.getRxApiService(context);

        rxApiService.login()

        consumer.get("login_response").accept();
    }

}
