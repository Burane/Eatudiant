package com.cnam.eatudiant.fragments.recipeDetails;

import android.os.Bundle;
import android.widget.ListView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.cnam.eatudiant.R;
import com.cnam.eatudiant.adapter.CookwareAdapter;
import com.cnam.eatudiant.adapter.IngredientAdapter;
import com.cnam.eatudiant.data.recipeDetails.Requirement;

import java.util.ArrayList;
import java.util.List;

public class IngredientFragment extends Fragment {
    @BindView(R.id.ingredientList)
    ListView ingredientListView;

    private static final String ARG_INGREDIENT = "ingredient";
    private ArrayList<Requirement> ingredientList;

    public IngredientFragment() {
        // Required empty public constructor
    }


    public static IngredientFragment newInstance(List<Requirement> ingredients) {
        IngredientFragment fragment = new IngredientFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_INGREDIENT, new ArrayList<>(ingredients));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ingredientList = getArguments().getParcelableArrayList(ARG_INGREDIENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_ingredient, container, false);
        ButterKnife.bind(this, root);

        IngredientAdapter ingredientAdapter = new IngredientAdapter(getActivity(), ingredientList);

        ingredientListView.setAdapter(ingredientAdapter);

        return root;
    }
}
