package com.cnam.eatudiant.store;

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

public class UserStore {

    private Map<String, Consumer> consumer;

    private RxUserApiService rxUserApiService;
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

        rxUserApiService = RxApiClient.getRxApiService(context);

//        Observable<User> userResponse = rxUserApiService.login(new UserAuth(username, password));
//
//        userResponse.subscribe(user -> {
//            Log.i("eatudiant_debug", "user " + user.toString());
//
//            //        consumer.get("login_response").accept(here the response to be handled by the view);
//
//        });

        Observable<List<FakeUser>> fakeUser = rxUserApiService.getUser();

        fakeUser.subscribe(fakeUser1 -> {
            Log.i("eatudiant_debug", "user " + Arrays.toString(fakeUser1.toArray()));
        });


    }

}
