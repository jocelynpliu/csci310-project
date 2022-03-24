package csci310.team53.easyteamup.handlers;

import android.util.Log;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;

import javax.annotation.Nullable;

import csci310.team53.easyteamup.EasyTeamUp;
import io.realm.mongodb.App;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;

/**
 * Handles user authentication and methods.
 *
 * @author Thomas Peters
 */
public class UserHandler {

    private final EasyTeamUp app;
    private final AtomicReference<User> userRef;

    /**
     * Setup user handler and nullable user reference.
     *
     * @param app instance of EasyTeamUp to access handlers
     */
    public UserHandler(EasyTeamUp app) {
        this.app = app;
        this.userRef = new AtomicReference<User>();
    }

    /**
     * Attempt to register the user to the database.
     *
     * @param username username of the user.
     * @param password password of the user.
     * @param success a callback function to be run in the event of a success.
     * @param fail a callback function to be run in the event of a failure.
     */
    public void register(String username, String password, Callable<Void> success, Callable<Void> fail) {
        App realm = app.getRealm();
        realm.getEmailPassword().registerUserAsync(username, password, result -> {
            try {
                if (result.isSuccess()) {
                    userRef.set(realm.currentUser());
                    success.call();
                } else {
                    Log.v("Registration Error", result.getError().getErrorMessage());
                    fail.call();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Login the user with MongoDB Realm using their email and password.
     * Will run the success or fail callback depending on result.
     *
     * @param username The string username of the account.
     * @param password The string password of the account.
     * @param success Callback to be run if login is successful.
     * @param fail Callback to be run if login fails.
     */
    public void login(String username, String password, Callable<Void> success, Callable<Void> fail) {
        App realm = app.getRealm();
        Credentials credentials = Credentials.emailPassword(username, password);
        realm.loginAsync(credentials, result -> {
            try {
                if (result.isSuccess()) {
                    userRef.set(realm.currentUser());
                    success.call();
                } else {
                    fail.call();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Getter for user. Accesses an atomic object.
     * @return User if authenticated, otherwise null.
     */
    @Nullable
    public User getUser() {
        return userRef.get();
    }

}

