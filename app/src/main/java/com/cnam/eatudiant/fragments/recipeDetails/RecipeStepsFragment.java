package com.cnam.eatudiant.fragments.recipeDetails;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.cnam.eatudiant.data.recipeDetails.Step;
import com.jakewharton.rxbinding4.view.RxView;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecipeStepsFragment extends Fragment {

    @BindView(R.id.stepText)
    TextView stepText;

    @BindView(R.id.nextButton)
    Button nextButton;

    @BindView(R.id.prevButton)
    Button prevButton;

    private static final String ARG_STEPS = "steps";
    private ArrayList<Step> stepList;
    private Step currentStep;
    private int currentStepIndex = 0;

    public RecipeStepsFragment() {
        // Required empty public constructor
    }


    public static RecipeStepsFragment newInstance(List<Step> steps) {
        RecipeStepsFragment fragment = new RecipeStepsFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_STEPS, new ArrayList<>(steps));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            stepList = getArguments().getParcelableArrayList(ARG_STEPS);
            currentStep = stepList.get(currentStepIndex);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_recipe_steps, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d(Config.LOG_TAG, Arrays.toString(stepList.toArray()));

        hideOrShowButton();
        setStepText();

        RxView.clicks(prevButton).subscribe(click -> {
            if (currentStepIndex > 0) {
                currentStepIndex--;
                currentStep = stepList.get(currentStepIndex);
                hideOrShowButton();
                setStepText();
            }
        });

        RxView.clicks(nextButton).subscribe(click -> {
            if (currentStepIndex < stepList.size()) {
                currentStepIndex++;
                currentStep = stepList.get(currentStepIndex);
                hideOrShowButton();
                setStepText();
            }
        });

    }

    private void hideOrShowButton() {
        prevButton.setVisibility(currentStepIndex == 0 ? View.GONE : View.VISIBLE);
        nextButton.setVisibility(currentStepIndex == stepList.size() - 1 ? View.GONE : View.VISIBLE);
    }

    private void setStepText() {
        stepText.setText(currentStep.getInstructions());
    }
}
