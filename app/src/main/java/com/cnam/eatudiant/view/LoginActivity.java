package com.cnam.eatudiant.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
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

public class LoginActivity extends AppCompatActivity implements View {

    @BindView(R.id.login_button)
    Button loginButton;

    @BindView(R.id.button_goToRegister)
    Button goToRegisterbutton;

    @BindView(R.id.input_password)
    TextInputEditText password;

    @BindView(R.id.input_username)
    TextInputEditText username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        new LoginIntent(this).start();

        RxView.clicks(goToRegisterbutton).subscribe(next -> {
            Intent intent = new Intent(this, RegisterActivity.class);

            startActivity(intent);
        });
    }

    @Override
    public Map<String, Observable<?>> getActions() {
        Map<String, Observable<?>> actions = new HashMap<>();
        actions.put("loginPressed", RxView.clicks(loginButton));
        actions.put("usernameChanged", RxTextView.textChanges(username));
        actions.put("passwordChanged", RxTextView.textChanges(password));
        return actions;
    }

    @Override
    public Map<String, Consumer<Object>> getConsumers() {
        Map<String, Consumer<Object>> consumers = new HashMap<>();
        consumers.put("login_failed", message -> {
            if (message instanceof String)
                loginFailed((String) message);
        });
        return consumers;
    }

    @Override
    public Context getContext() {
        return this.getApplicationContext();
    }

    private void loginFailed(String message) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }
}
