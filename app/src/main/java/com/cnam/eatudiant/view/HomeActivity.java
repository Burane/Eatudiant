package com.cnam.eatudiant.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import com.cnam.eatudiant.R;
import com.cnam.eatudiant.data.recipe.Recipe;
import com.cnam.eatudiant.intent.HomeIntent;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity implements View {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        new HomeIntent(this).start();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public Map<String, Observable<?>> getActions() {
        Map<String, Observable<?>> actions = new HashMap<>();
        actions.put("getRecipes", Observable.empty());
        return actions;
    }

    @Override
    public Map<String, Consumer<Object>> getConsumers() {
        Map<String, Consumer<Object>> consumers = new HashMap<>();
        consumers.put("recipesResponse", recipes -> {
            if (recipes instanceof List) {
                List<Recipe> recipesList = (List<Recipe>) recipes;
                showRecipesList(recipesList);
            }
        });
        return consumers;
    }

    private void showRecipesList(List<Recipe> recipes) {
        Log.i("eatudiant_debug", "showRecipesList");

    }

    @Override
    public Context getContext() {
        return this.getApplicationContext();
    }
}
