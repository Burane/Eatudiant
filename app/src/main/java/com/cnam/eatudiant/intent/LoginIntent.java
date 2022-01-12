package com.cnam.eatudiant.intent;

import android.util.Log;
import com.cnam.eatudiant.model.LoginModel;
import com.cnam.eatudiant.view.View;
import io.reactivex.rxjava3.core.Observable;

import java.util.Map;

public class LoginIntent {
    private Map<String, Observable> actions;
    private LoginModel loginModel;

    public LoginIntent(View userView) {
        actions = userView.getActions();
        loginModel = new LoginModel(userView.getConsumers(), userView.getContext());
    }

    @SuppressWarnings("unchecked")
    public void start() {
        actions.get("loginPressed").subscribe(next -> {
            loginModel.login();
        });

        actions.get("usernameChanged").subscribe(next -> {
            loginModel.setUsername(next.toString());
        });

        actions.get("passwordChanged").subscribe(next -> {
            loginModel.setPassword(next.toString());
        });
    }
}
