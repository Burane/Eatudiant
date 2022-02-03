package com.cnam.eatudiant.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.cnam.eatudiant.R;
import com.cnam.eatudiant.intent.LoginIntent;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.jakewharton.rxbinding4.view.RxView;
import com.jakewharton.rxbinding4.widget.RxTextView;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;

import java.util.HashMap;
import java.util.Map;

public class RecipeActivity extends AppCompatActivity implements View {

    @BindView(R.id.button_goToNextStep)
    Button goToNextStepButton;

    @BindView(R.id.button_goToPreviousStep)
    Button goToPreviousStepButton;

    @Override
    public Map<String, Observable> getActions() {
        return null;
    }

    @Override
    public Map<String, Consumer> getConsumers() {
        return null;
    }

    @Override
    public Context getContext() {
        return null;
    }
}
