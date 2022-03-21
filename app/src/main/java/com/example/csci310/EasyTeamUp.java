package com.example.csci310;

import android.app.Application;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;

/**
 * The main application class for EasyTeamUp.
 * Initializes all handlers and database for use in activities.
 *
 * @author Thomas Peters
 */
public class EasyTeamUp extends Application {

    private App realm;
    private UserHandler userHandler;

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize MongoDB Realm Database
        Realm.init(this);
        realm = new App(new AppConfiguration.Builder("easyteamup-fysss").build());

        // Initialize handlers
        userHandler = new UserHandler(this);
    }

    /**
     *
     * @return
     */
    public App getRealm() {
        return realm;
    }

    public UserHandler getUserHandler() {
        return userHandler;
    }
}
