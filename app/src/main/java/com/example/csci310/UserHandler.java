package com.example.csci310;

import android.util.Log;

public class UserHandler {
    // private PasswordEncrypter??
    private User user;

    public boolean register(String email, String username, String password) {
        // implement
        String TAG = "REGISTER";
        Log.v(TAG, "email: " + email + "\n username: " + username + "\n password: " + password);

        return true; // so it compiles
    }

    public boolean login(String email, String password) {
        // implement
        return false; // so it compiles
    }

}

