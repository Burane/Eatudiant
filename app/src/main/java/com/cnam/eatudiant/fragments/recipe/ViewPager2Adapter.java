package com.cnam.eatudiant.fragments.recipe;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ViewPager2Adapter extends FragmentStateAdapter {

    @Setter
    private ArrayList<Fragment> fragments;


    public ViewPager2Adapter(@NonNull @NotNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @NotNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }
}
