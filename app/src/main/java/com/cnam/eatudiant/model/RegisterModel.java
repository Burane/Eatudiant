package com.cnam.eatudiant.model;

import android.content.Context;
import android.util.Log;
import com.cnam.eatudiant.api.RxApiClient;
import com.cnam.eatudiant.api.RxUserApiService;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;
import lombok.Setter;
import lombok.SneakyThrows;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class RegisterModel {


    private Map<String, Consumer<Object>> consumer;

    private RxUserApiService rxUserApiService;
    private Context context;

    @Setter
    private String username = "";
    @Setter
    private String password = "";
    @Setter
    private String passwordConfirmation = "";
    @Setter
    private String email = "";

    public RegisterModel(Map<String, Consumer<Object>> consumer, Context context) {
        this.consumer = consumer;
        this.context = context;
    }

    @SneakyThrows
    public void register() {

        rxUserApiService = RxApiClient.getRxApiService(context);

//        Observable<User> userResponse = rxUserApiService.login(new UserAuth(username, password));
//
//        userResponse.subscribe(user -> {
//            Log.i("eatudiant_debug", "user " + user.toString());
//
//            //        consumer.get("login_response").accept(here the response to be handled by the view);
//
//        });



    }

}
