package com.cnam.eatudiant.view;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.cnam.eatudiant.R;
import com.cnam.eatudiant.data.recipe.Recipe;
import com.cnam.eatudiant.data.recipeDetails.FullRecipe;
import com.cnam.eatudiant.data.recipeDetails.Steps;
import com.cnam.eatudiant.fragments.recipe.*;
import com.cnam.eatudiant.fragments.recipeDetails.CookwareFragment;
import com.cnam.eatudiant.fragments.recipeDetails.IngredientFragment;
import com.cnam.eatudiant.fragments.recipeDetails.ViewPageAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class RecipeDetailsActivity extends AppCompatActivity implements View{

    @BindView(R.id.view_pager)
    ViewPager2 viewPager2;

    @BindView(R.id.tabs)
    TabLayout tabLayout;

    ArrayList<String> titles =  new ArrayList<>(Arrays.asList("Cookware", "Steps", "Ingredients"));
    ViewPageAdapter viewPageAdapter;
    ArrayList<Fragment> fragmentList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ButterKnife.bind(this);

        viewPageAdapter = new ViewPageAdapter(this);
        fragmentList = new ArrayList<>();
        fragmentList.add(new Fragment());
        fragmentList.add(new Fragment());
        fragmentList.add(new Fragment());
        viewPageAdapter.setFragments(fragmentList);

        viewPager2.setAdapter(viewPageAdapter);

        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> tab.setText(titles.get(position))).attach();
    }


    @Override
    public Map<String, Observable<?>> getActions() {
        Map<String, Observable<?>> actions = new HashMap<>();
        // throw an event with a empty object since this value is never used
        actions.put("getFullRecipe", Observable.just(new Object()));
        actions.put("getRecipeSteps", Observable.just(new Object()));
        return actions;
    }

    @Override
    public Map<String, Consumer<Object>> getConsumers() {
        Map<String, Consumer<Object>> consumers = new HashMap<>();
        consumers.put("recipeStepsResponse", steps -> {
            if (steps instanceof Steps) {
                Steps steps1 = (Steps) steps;
            }
        });
        consumers.put("fullRecipeResponse", fullRecipe -> {
            if (fullRecipe instanceof FullRecipe) {
                FullRecipe fullRecipe1 = (FullRecipe) fullRecipe;


            }
        });
        return consumers;
    }

    @Override
    public Context getContext() {
        return this.getApplicationContext();
    }
}
