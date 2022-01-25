package com.cnam.eatudiant.model;

import android.content.Context;
import android.util.Log;
import com.cnam.eatudiant.api.RxApiClient;
import com.cnam.eatudiant.api.RxUserApiService;
import com.cnam.eatudiant.data.FakeUser;
import com.cnam.eatudiant.data.User;
import com.cnam.eatudiant.data.UserAuth;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;
import lombok.Setter;
import lombok.SneakyThrows;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class LoginModel {

    private Map<String, Consumer> consumer;

    private RxUserApiService rxUserApiService;
    private Context context;

    @Setter
    private String username = "";
    @Setter
    private String password = "";

    public LoginModel(Map<String, Consumer> consumer, Context context) {
        this.consumer = consumer;
        this.context = context;
    }

    @SneakyThrows
    public void login() {
        Log.i("eatudiant_debug", "login");

        rxUserApiService = RxApiClient.getRxApiService(context);

        Observable<User> userResponse = rxUserApiService.login(new UserAuth(username, password));

        userResponse.subscribe(user -> {
            Log.i("eatudiant_debug", "user resposne");
            Log.i("eatudiant_debug", "user " + user.toString());

            //        consumer.get("login_response").accept(here the response to be handled by the view);

        });

    }

}
