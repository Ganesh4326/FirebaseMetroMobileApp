package com.example.firebasemymetro.UserManager;

import android.content.Context;
import android.content.SharedPreferences;

public class UserManager {

    private static final String USER_PREFS = "user_prefs";
    private static final String USER_ID_KEY = "user_id";
    private static final String USER_NAME_KEY = "user_name";

    private static final int USER_COINS = 0;

    private final SharedPreferences sharedPreferences;

    public UserManager(Context context) {
        this.sharedPreferences = context.getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE);
    }

    public void saveUserData(String userId, String userName, int coins) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_ID_KEY, userId);
        editor.putString(USER_NAME_KEY, userName);
        editor.putInt(String.valueOf(USER_COINS), coins);
        // Add other user data as needed
        editor.apply();
    }

    public String getUserId() {
        return sharedPreferences.getString(USER_ID_KEY, null);
    }

    public String getUserName() {
        return sharedPreferences.getString(USER_NAME_KEY, null);
    }

    public String getUserCoins(){
        return sharedPreferences.getString(String.valueOf(USER_COINS), null);
    }
}

