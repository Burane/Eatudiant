package com.cnam.eatudiant.fragments.recipe;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.cnam.eatudiant.Config;
import com.cnam.eatudiant.R;
import com.cnam.eatudiant.adapter.RecipeAdapter;
import com.cnam.eatudiant.data.recipe.Recipe;
import com.cnam.eatudiant.databinding.FragmentRecipeBinding;
import com.cnam.eatudiant.intent.RecipeIntent;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;

import java.util.*;

public class RecipeFragment extends Fragment implements com.cnam.eatudiant.view.View {

    private FragmentRecipeBinding binding;
    private RecipeAdapter recipeAdapter;

    @BindView(R.id.recipesList)
    ListView listView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentRecipeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        ButterKnife.bind(this, root);

        new RecipeIntent(this).start();

        recipeAdapter = new RecipeAdapter(getActivity(), new ArrayList<>());
        Log.d(Config.LOG_TAG, recipeAdapter.toString());
        listView.setAdapter(recipeAdapter);


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public Map<String, Observable<?>> getActions() {
        Map<String, Observable<?>> actions = new HashMap<>();
        // throw an event with a empty object since this value is never used
        actions.put("getRecipes", Observable.just(new Object()));
        return actions;
    }

    @Override
    public Map<String, Consumer<Object>> getConsumers() {
        Map<String, Consumer<Object>> consumers = new HashMap<>();
        consumers.put("recipesResponse", recipes -> {
            if (recipes instanceof List) {
                ArrayList<Recipe> recipesList = (ArrayList<Recipe>) recipes;
                showRecipesList(recipesList);
            }
        });
        return consumers;
    }

    @Override
    public Context getContext() {
        return this.getActivity().getApplicationContext();
    }


    private void showRecipesList(ArrayList<Recipe> recipes) {
        recipeAdapter.getItems().clear();
        recipeAdapter.getItems().addAll(recipes);
        recipeAdapter.notifyDataSetChanged();
    }

}
