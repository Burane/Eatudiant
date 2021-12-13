package com.cnam.eatudiant;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.cnam.eatudiant.ui.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(this.getClass().getSimpleName(),"on create");

    }

    public void onGoToLogin(View view) {
        Log.i(TAG,"clicking onGoToLogin");
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

}
