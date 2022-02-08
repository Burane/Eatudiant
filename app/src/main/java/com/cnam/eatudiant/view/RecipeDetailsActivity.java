package com.cnam.eatudiant.view;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.cnam.eatudiant.R;
import com.cnam.eatudiant.fragments.recipe.*;
import com.cnam.eatudiant.fragments.recipeDetails.CookwareFragment;
import com.cnam.eatudiant.fragments.recipeDetails.IngredientFragment;
import com.cnam.eatudiant.fragments.recipeDetails.ViewPageAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;

public class RecipeDetailsActivity extends AppCompatActivity {

    @BindView(R.id.view_pager)
    ViewPager2 viewPager2;

    @BindView(R.id.tabs)
    TabLayout tabLayout;

    ArrayList<String> titles =  new ArrayList<>(Arrays.asList("Cookware", "Steps", "Ingredients"));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ButterKnife.bind(this);

        ViewPageAdapter viewPageAdapter = new ViewPageAdapter(this);
        ArrayList<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(CookwareFragment.newInstance(0));
        fragmentList.add(CookwareFragment.newInstance(1));
        fragmentList.add(CookwareFragment.newInstance(2));
        viewPageAdapter.setFragments(fragmentList);



        viewPager2.setAdapter(viewPageAdapter);

        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> tab.setText(titles.get(position))).attach();
    }




}
