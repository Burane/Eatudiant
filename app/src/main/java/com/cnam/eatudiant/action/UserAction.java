package com.cnam.eatudiant.action;

import com.cnam.eatudiant.data.UserAuth;
import com.cnam.eatudiant.store.UserStore;
import com.cnam.eatudiant.view.View;
import io.reactivex.rxjava3.core.Observable;

import java.util.Map;

public class UserAction {
    private Map<String, Observable> actions;
    private UserStore userStore;

    UserAction(View userView) {
        actions = userView.getActions();
        userStore = new UserStore(userView.getConsumers());
    }

    @SuppressWarnings("unchecked")
    public void start() {
        actions.get("login").subscribe(next -> userStore.login());
    }
}
