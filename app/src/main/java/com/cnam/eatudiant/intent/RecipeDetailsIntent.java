package com.cnam.eatudiant.intent;

import android.util.Log;
import com.cnam.eatudiant.model.RecipeDetailsModel;
import com.cnam.eatudiant.model.RecipeModel;
import com.cnam.eatudiant.view.View;
import io.reactivex.rxjava3.core.Observable;

import java.util.Map;

public class RecipeDetailsIntent {

    private Map<String, Observable<?>> actions;
    private RecipeDetailsModel recipeDetailsModel;

    public RecipeDetailsIntent(View registerView) {
        actions = registerView.getActions();
        recipeDetailsModel = new RecipeDetailsModel(registerView.getConsumers(), registerView.getContext());
    }

    public void start() {
        actions.get("getFullRecipe").subscribe(id -> {
            recipeDetailsModel.getFullRecipe((int) id);
        });

        actions.get("getRecipeSteps").subscribe(id -> {
            recipeDetailsModel.getRecipeSteps((int) id);
        });

    }

}
