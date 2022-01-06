package com.cnam.eatudiant.store;

import android.content.Context;
import android.util.Log;
import com.cnam.eatudiant.api.RxApiClient;
import com.cnam.eatudiant.api.RxApiService;
import com.cnam.eatudiant.data.User;
import com.cnam.eatudiant.data.UserAuth;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;
import lombok.Setter;
import lombok.SneakyThrows;

import java.util.Map;

public class UserStore {

    private Map<String, Consumer> consumer;

    private RxApiService rxApiService;
    private Context context;

    @Setter
    private String username = "";
    @Setter
    private String password = "";

    public UserStore(Map<String, Consumer> consumer, Context context) {
        this.consumer = consumer;
        this.context = context;
    }

    @SneakyThrows
    public void login() {

        rxApiService = RxApiClient.getRxApiService(context);

        Observable<User> userResponse = rxApiService.login(new UserAuth(username, password));

        userResponse.subscribe(user -> {
            Log.i("eatudiant_debug", "user " + user.toString());

            //        consumer.get("login_response").accept(here the response to be handled by the view);

        });

        Log.i("eatudiant_debug", userResponse.toString());

    }

}
