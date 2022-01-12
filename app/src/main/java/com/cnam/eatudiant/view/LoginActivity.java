package com.cnam.eatudiant.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.cnam.eatudiant.R;
import com.cnam.eatudiant.action.UserAction;
import com.cnam.eatudiant.data.User;
import com.google.android.material.textfield.TextInputEditText;
import com.jakewharton.rxbinding4.material.RxTabLayout;
import com.jakewharton.rxbinding4.view.RxView;
import com.jakewharton.rxbinding4.widget.RxTextView;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View {

    @BindView(R.id.login_button)
    Button loginButton;

    @BindView(R.id.input_password)
    TextInputEditText password;

    @BindView(R.id.input_username)
    TextInputEditText username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        new UserAction(this).start();
    }

    @Override
    public Map<String, Observable> getActions() {
        Map<String, Observable> actions = new HashMap<>();
        actions.put("loginPressed", RxView.clicks(loginButton));
        actions.put("usernameChanged", RxTextView.textChanges(username));
        actions.put("passwordChanged", RxTextView.textChanges(password));
        return actions;
    }

    @Override
    public Map<String, Consumer> getConsumers() {
        Map<String, Consumer> consumers = new HashMap<>();
        return consumers;
    }

    @Override
    public Context getContext() {
        return this.getApplicationContext();
    }
}
