package com.cnam.eatudiant.intent;

import com.cnam.eatudiant.model.RecipeModel;
import com.cnam.eatudiant.view.View;
import io.reactivex.rxjava3.core.Observable;

import java.util.Map;

public class RecipeIntent {

    private Map<String, Observable<?>> actions;
    private RecipeModel homeModel;

    public RecipeIntent(View registerView) {
        actions = registerView.getActions();
        homeModel = new RecipeModel(registerView.getConsumers(), registerView.getContext());
    }

    public void start() {
        actions.get("getRecipes").subscribe(next -> {
            homeModel.getRecipes();
        });

    }

}
