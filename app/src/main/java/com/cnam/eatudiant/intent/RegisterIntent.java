package com.cnam.eatudiant.intent;

import android.util.Log;
import com.cnam.eatudiant.model.LoginModel;
import com.cnam.eatudiant.model.RegisterModel;
import com.cnam.eatudiant.view.View;
import io.reactivex.rxjava3.core.Observable;

import java.util.Map;

public class RegisterIntent {

    private Map<String, Observable> actions;
    private RegisterModel registerModel;

    public RegisterIntent(View registerView) {
        actions = registerView.getActions();
        registerModel = new RegisterModel(registerView.getConsumers(), registerView.getContext());
    }

    @SuppressWarnings("unchecked")
    public void start() {
        actions.get("registerPressed").subscribe(next -> {
            registerModel.register();
        });

        actions.get("usernameChanged").subscribe(next -> {
            registerModel.setUsername(next.toString());
        });

        actions.get("passwordChanged").subscribe(next -> {
            registerModel.setPassword(next.toString());
        });

        actions.get("passwordConfirmationChanged").subscribe(next -> {
            registerModel.setPasswordConfirmation(next.toString());
        });

        actions.get("emailChanged").subscribe(next -> {
            registerModel.setEmail(next.toString());
        });
    }

}
