package com.cnam.eatudiant.fragments.recipeDetails;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.cnam.eatudiant.Config;
import com.cnam.eatudiant.R;
import com.cnam.eatudiant.adapter.CookwareAdapter;
import com.cnam.eatudiant.data.recipeDetails.Requirement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CookwareFragment extends Fragment {

    @BindView(R.id.cookwareList)
    ListView cookwareListView;

    private static final String ARG_COOKWARE = "cookware";
    private ArrayList<Requirement> cookwareList;

    public CookwareFragment() {
        // Required empty public constructor
    }


    public static CookwareFragment newInstance(List<Requirement> cookware) {
        CookwareFragment fragment = new CookwareFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_COOKWARE, new ArrayList<>(cookware));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cookwareList = getArguments().getParcelableArrayList(ARG_COOKWARE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_cookware, container, false);
        ButterKnife.bind(this, root);

        CookwareAdapter cookwareAdapter = new CookwareAdapter(getActivity(), cookwareList);

        cookwareListView.setAdapter(cookwareAdapter);

        return root;
    }

}
