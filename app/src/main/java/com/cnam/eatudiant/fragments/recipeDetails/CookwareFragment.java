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

    private int[] COLOR_MAP = {
            R.color.red_100, R.color.red_300, R.color.red_500, R.color.red_700, R.color.blue_100,
            R.color.blue_300, R.color.blue_500, R.color.blue_700, R.color.green_100, R.color.green_300,
            R.color.green_500, R.color.green_700
    };

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

        Log.i("eatudiant_debug", "cookwareList : " + Arrays.toString(cookwareList.toArray()));
        Log.i("eatudiant_debug", "cookwareListView : " + cookwareListView);

        CookwareAdapter cookwareAdapter = new CookwareAdapter(getActivity(), cookwareList);

        cookwareListView.setAdapter(cookwareAdapter);

        return root;
    }

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        view.setBackgroundColor(ContextCompat.getColor(getContext(), COLOR_MAP[counter]));
//        TextView textViewCounter = view.findViewById(R.id.tv_counter);
//        textViewCounter.setText("Fragment No " + (counter + 1));
//    }
}
