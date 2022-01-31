package com.cnam.eatudiant;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import butterknife.ButterKnife;
import com.cnam.eatudiant.utils.SessionManager;
import com.cnam.eatudiant.view.LoginActivity;

public class MainActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();
    private final int SPLASH_SCREEN_DELAY = 3000;

    Animation topAnim;
    Animation bottomAnim;
    ImageView logo;
    TextView title;
    TextView slogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            final WindowInsetsController insetsController = getWindow().getInsetsController();
            if (insetsController != null) {
                insetsController.hide(WindowInsets.Type.statusBars());
            }
        } else {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
            );
        }


        // check if authToken is in shared pref
        // if yes login and go to homeActivity
        // if no go to loginActivity

        SessionManager sessionManager = new SessionManager(this.getApplicationContext());
        String token = sessionManager.fetchAuthToken();

        Log.i("eatudiant_debug", "token : " + token);


        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        logo = findViewById(R.id.logo);
        title = findViewById(R.id.title);
        slogan = findViewById(R.id.slogan);

        logo.setAnimation(topAnim);
        title.setAnimation(bottomAnim);
        slogan.setAnimation(bottomAnim);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(this, LoginActivity.class);

            Pair[] pairs = new Pair[2];
            pairs[0] = new Pair<View, String>(logo, "logo");
            pairs[1] = new Pair<View, String>(title, "title");

            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, pairs);

            startActivity(intent, options.toBundle());
            finish();
        }, SPLASH_SCREEN_DELAY);
    }

}
