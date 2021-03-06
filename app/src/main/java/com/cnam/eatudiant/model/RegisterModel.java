package com.cnam.eatudiant.model;

import android.content.Context;
import com.cnam.eatudiant.R;
import com.cnam.eatudiant.api.RxApiClient;
import com.cnam.eatudiant.api.RxUserApiService;
import com.cnam.eatudiant.data.register.RegisterBody;
import com.cnam.eatudiant.data.register.RegisterResponse;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;
import lombok.Setter;
import lombok.SneakyThrows;
import retrofit2.HttpException;

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

        if (!password.equals(passwordConfirmation)) {
            consumer.get("passwordDontMatch").accept(context.getResources().getString(R.string.password_dont_match));
        } else {


            Observable<RegisterResponse> registerResponse = rxUserApiService.register(new RegisterBody(username, password, email));

            registerResponse
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> {
                                consumer.get("registerSuccess").accept("success");

                            },
                            throwable -> {
                                if (throwable instanceof HttpException) {
                                    HttpException err = (HttpException) throwable;
                                    String errMsg = err.message().trim().equals("") ? context.getResources().getString(R.string.register_failed) : err.message().trim();
                                    consumer.get("registerError").accept(errMsg);
                                }

                            });
        }


    }
}
