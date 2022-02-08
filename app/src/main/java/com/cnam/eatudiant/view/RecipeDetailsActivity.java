package com.cnam.eatudiant.view;

import android.os.Bundle;
import android.widget.TableLayout;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.cnam.eatudiant.R;
import com.cnam.eatudiant.fragments.recipe.*;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RecipeDetailsActivity extends AppCompatActivity implements TabLayoutMediator.TabConfigurationStrategy{

    @BindView(R.id.viewPager2)
    ViewPager2 viewPager2;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    ArrayList<String> titles;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ButterKnife.bind(this);

        titles = new ArrayList<>();
        titles.add("Cookware");
        titles.add("Recipe steps");
        titles.add("Ingredients");
        setViewPagerAdapter();
        new TabLayoutMediator(tabLayout, viewPager2, this).attach();
    }


    public void setViewPagerAdapter() {
        ViewPager2Adapter viewPager2Adapter = new ViewPager2Adapter(this);
        ArrayList<Fragment> fragmentList = new ArrayList<>(); //creates
        fragmentList.add(CookwareFragment.newInstance("",""));
        fragmentList.add(RecipeStepsFragment.newInstance("",""));
        fragmentList.add(IngredientFragment.newInstance("",""));

        viewPager2Adapter.setFragments(fragmentList);
        viewPager2.setAdapter(viewPager2Adapter);
    }


    @Override
    public void onConfigureTab(@NonNull @NotNull TabLayout.Tab tab, int position) {
        tab.setText(titles.get(position));
    }
}
