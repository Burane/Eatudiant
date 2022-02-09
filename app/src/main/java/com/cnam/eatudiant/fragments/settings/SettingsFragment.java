package com.cnam.eatudiant.fragments.settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import androidx.preference.PreferenceFragmentCompat;
import com.cnam.eatudiant.Config;
import com.cnam.eatudiant.R;
import com.cnam.eatudiant.utils.SessionManager;
import com.cnam.eatudiant.view.LoginActivity;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

        findPreference("logout")
                .setOnPreferenceClickListener(
                        preference -> {
                            this.logoutUser();
                            return true;
                        });
        /*
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, this)
                .commit();

         */
    }

    private void logoutUser(){
        SessionManager sessionManager = new SessionManager(this.requireContext());
        sessionManager.removeAuthToken();
        startActivity(new Intent(this.getActivity(), LoginActivity.class));
    }

    @Override
    public void onResume() {
        super.onResume();

        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();

        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        // TODO handle pref change
        if (key.equals("logout")) {
            Log.i(Config.LOG_TAG, "logout pref clicked");
            // Preference connectionPref = findPreference(key);
            // Set summary to be the user-description for the selected value
            // connectionPref.setSummary(sharedPreferences.getString(key, ""));
            this.logoutUser();
        }
    }
}
