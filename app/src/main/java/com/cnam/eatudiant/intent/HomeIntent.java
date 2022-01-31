package com.cnam.eatudiant.intent;

import com.cnam.eatudiant.model.HomeModel;
import com.cnam.eatudiant.model.RegisterModel;
import com.cnam.eatudiant.view.View;
import io.reactivex.rxjava3.core.Observable;

import java.util.Map;

public class HomeIntent {

    private Map<String, Observable<?>> actions;
    private HomeModel homeModel;

    public HomeIntent(View registerView) {
        actions = registerView.getActions();
        homeModel = new HomeModel(registerView.getConsumers(), registerView.getContext());
    }

    public void start() {
        actions.get("getRecipes").subscribe(next -> {
            homeModel.getRecipes();
        });

    }

}
