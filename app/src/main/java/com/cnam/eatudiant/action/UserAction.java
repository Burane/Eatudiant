package com.cnam.eatudiant.action;

import android.content.Context;
import android.util.Log;
import com.cnam.eatudiant.data.UserAuth;
import com.cnam.eatudiant.store.UserStore;
import com.cnam.eatudiant.view.View;
import io.reactivex.rxjava3.core.Observable;

import java.util.Map;

public class UserAction {
    private Map<String, Observable> actions;
    private UserStore userStore;

    public UserAction(View userView) {
        actions = userView.getActions();
        userStore = new UserStore(userView.getConsumers(), userView.getContext());
    }

    @SuppressWarnings("unchecked")
    public void start() {
        actions.get("login").subscribe(next -> {
            Log.i("eatudiant_debug", next.toString());

            userStore.login();
        });
    }
}
