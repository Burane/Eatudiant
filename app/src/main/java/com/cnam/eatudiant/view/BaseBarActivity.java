package com.cnam.eatudiant.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;
import com.cnam.eatudiant.R;

public abstract class BaseBarActivity extends AppCompatActivity {
    protected AppBarConfiguration barConfiguration;

    protected AppBarConfiguration setupAppBar() {
        return new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_map, R.id.nav_slideshow
        )
                .build();
    }

}
