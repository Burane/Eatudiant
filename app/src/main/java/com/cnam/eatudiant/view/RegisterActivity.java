package com.cnam.eatudiant.view;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.cnam.eatudiant.R;
import com.cnam.eatudiant.intent.LoginIntent;
import com.cnam.eatudiant.intent.RegisterIntent;
import com.google.android.material.textfield.TextInputEditText;
import com.jakewharton.rxbinding4.view.RxView;
import com.jakewharton.rxbinding4.widget.RxTextView;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements View {

    @BindView(R.id.input_username)
    TextInputEditText username;

    @BindView(R.id.input_email)
    TextInputEditText email;

    @BindView(R.id.input_password)
    TextInputEditText password;

    @BindView(R.id.input_confirm_password)
    TextInputEditText confirmPassword;

    @BindView(R.id.button_register)
    Button registerButton;

    @BindView(R.id.button_goToLogin)
    Button goToLoginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        new RegisterIntent(this).start();

        RxView.clicks(goToLoginButton).subscribe( next -> {
            Intent intent = new Intent(this, LoginActivity.class);

            startActivity(intent);
            finish();
        });


    }

    @Override
    public Map<String, Observable<?>> getActions() {
        Map<String, Observable<?>> actions = new HashMap<>();
        actions.put("registerPressed", RxView.clicks(registerButton));
        actions.put("usernameChanged", RxTextView.textChanges(username));
        actions.put("passwordChanged", RxTextView.textChanges(password));
        actions.put("passwordConfirmationChanged", RxTextView.textChanges(confirmPassword));
        actions.put("emailChanged", RxTextView.textChanges(email));
        return actions;
    }

    @Override
    public Map<String, Consumer<Object>> getConsumers() {
        Map<String, Consumer<Object>> consumers = new HashMap<>();
        return consumers;
    }

    @Override
    public Context getContext() {
        return this.getApplicationContext();
    }
}
