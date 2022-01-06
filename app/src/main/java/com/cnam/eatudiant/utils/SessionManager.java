package com.cnam.eatudiant.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.cnam.eatudiant.R;

public class SessionManager {

    private final String USER_TOKEN = "user_token";

    private SharedPreferences sharedPreferences;

    public SessionManager(Context context) {
        this.sharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
    }

    public void saveAuthToken(String token) {
        sharedPreferences.edit()
                .putString(USER_TOKEN, token)
                .apply();
    }

    public String fetchAuthToken() {
        return sharedPreferences.getString(USER_TOKEN,null);
    }
}
