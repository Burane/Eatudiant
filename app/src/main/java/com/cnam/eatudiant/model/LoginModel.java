package com.cnam.eatudiant.model;

import android.content.Context;
import android.util.Log;
import com.cnam.eatudiant.api.RxApiClient;
import com.cnam.eatudiant.api.RxUserApiService;
import com.cnam.eatudiant.data.Response;
import com.cnam.eatudiant.data.User;
import com.cnam.eatudiant.data.UserAuth;
import com.cnam.eatudiant.utils.SessionManager;
import com.squareup.moshi.Moshi;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;
import lombok.Setter;
import lombok.SneakyThrows;
import retrofit2.HttpException;

import java.util.Map;

public class LoginModel {

    private Map<String, Consumer<Object>> consumer;

    private RxUserApiService rxUserApiService;
    private Context context;

    @Setter
    private String username = "";
    @Setter
    private String password = "";

    public LoginModel(Map<String, Consumer<Object>> consumer, Context context) {
        this.consumer = consumer;
        this.context = context;
    }

    @SneakyThrows
    public void login() {
        Log.i("eatudiant_debug", "login");

        rxUserApiService = RxApiClient.getRxApiService(context);


        Observable<Response<User>> userResponse = rxUserApiService.login(new UserAuth(username, password));

        userResponse.subscribe(
                response -> {
                    Log.i("eatudiant_debug", "user response");
                    Log.i("eatudiant_debug", "user " + response.toString());

                    SessionManager sessionManager = new SessionManager(context);
                    sessionManager.saveAuthToken(response.getDatas().getToken());

                    consumer.get("login_success").accept("success");

                },
                throwable -> {
                    if (throwable instanceof HttpException) {
                        HttpException err = (HttpException) throwable;
                        Log.i("eatudiant_debug", err.message());
                        consumer.get("login_failed").accept(err.message());
                    }

                });

    }

    public void logout() {
        Log.i("eatudiant_debug", "logout");
        SessionManager sessionManager = new SessionManager(context);
        sessionManager.removeAuthToken();
    }

}
