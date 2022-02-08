package com.cnam.eatudiant.view;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.cnam.eatudiant.R;
import com.cnam.eatudiant.fragments.recipe.CookwareFragment;
import com.cnam.eatudiant.fragments.recipe.IngredientFragment;
import com.cnam.eatudiant.fragments.recipe.RecipeFragment;
import com.cnam.eatudiant.fragments.recipe.ViewPager2Adapter;

import java.util.ArrayList;

public class RecipeDetailsActivity extends AppCompatActivity {

    @BindView(R.id.viewPager2)
    ViewPager2 viewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        ButterKnife.bind(this);


    }


    public void setViewPagerAdapter() {
        ViewPager2Adapter viewPager2Adapter = new ViewPager2Adapter(this);
        ArrayList<Fragment> fragmentList = new ArrayList<>(); //creates
        fragmentList.add(new CookwareFragment());
        fragmentList.add(new RecipeFragment());
        fragmentList.add(new IngredientFragment());

        viewPager2Adapter.setFragments(fragmentList);
        viewPager2.setAdapter(viewPager2Adapter);
    }


}
