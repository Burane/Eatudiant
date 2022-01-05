package com.cnam.eatudiant.store;

import android.util.Log;
import com.cnam.eatudiant.data.User;
import com.cnam.eatudiant.data.UserAuth;
import io.reactivex.rxjava3.functions.Consumer;

import java.util.Map;

public class UserStore extends Store {

    private User user;

    public UserStore(Map<String, Consumer> consumer) {
        super(consumer);
        this.user = null;
    }

    public void login() {
        Log.i("eatudiant", "login: ");
        getAction("login").accept(user);
    }

}
