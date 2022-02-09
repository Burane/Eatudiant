package com.cnam.eatudiant.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.cnam.eatudiant.R;
import com.cnam.eatudiant.fragments.settings.SettingsFragment;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public abstract class BaseBarActivity extends AppCompatActivity {
    private AppBarConfiguration barConfiguration;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    protected void initNavigationToolbar(
            Toolbar toolbar,
            DrawerLayout drawer,
            NavigationView navigationView
    ) {
        setSupportActionBar(toolbar);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        barConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_map, R.id.action_settings
        )
                .setOpenableLayout(drawer)
                .build();
        NavHostFragment navHost = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_content_home);
        NavController navController = Objects.requireNonNull(navHost).getNavController();
        NavigationUI.setupActionBarWithNavController(this, navController, barConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        return NavigationUI.navigateUp(navController, barConfiguration)
                || super.onSupportNavigateUp();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection

        if (item.getItemId() == R.id.action_settings) {
            Intent settings = new Intent(this, SettingsActivity.class);
            // go to settings screen
            startActivity(settings);
            /*
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();

             */
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
