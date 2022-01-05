package com.cnam.eatudiant.view;

import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import com.cnam.eatudiant.R;
import com.cnam.eatudiant.data.User;
import com.google.android.material.textfield.TextInputEditText;
import com.jakewharton.rxbinding4.view.RxView;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View {

    @BindView(R.id.login_button)
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    public Map<String, Observable> getActions() {
        Map<String, Observable> actions = new HashMap<>();
        actions.put("login", RxView.clicks(loginButton));
        return actions;
    }

    @Override
    public Map<String, Consumer> getConsumers() {
        Map<String, Consumer> consumers = new HashMap<>();
        return consumers;
    }
}
