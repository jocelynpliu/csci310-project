package csci310.team53.easyteamup.handlers;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import org.bson.Document;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;

import javax.annotation.Nullable;

import csci310.team53.easyteamup.EasyTeamUp;
import csci310.team53.easyteamup.activities.HomeActivity;
import csci310.team53.easyteamup.activities.LoginActivity;
import csci310.team53.easyteamup.activities.MapsActivity;
import io.realm.mongodb.App;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.RealmResultTask;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.iterable.MongoCursor;

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

    public void logout(Context context) {
        App realm = app.getRealm();
        realm.currentUser().logOutAsync(result -> {
            if (result.isSuccess()) {
                Intent intent = new Intent(context, LoginActivity.class);
                context.startActivity(intent);
            } else {
                Toast.makeText(context, "Unable to sign out!", Toast.LENGTH_SHORT).show();
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

    /**
     * Retrieves all users from the database (in alphabetical order).
     * @return an async task to work with.
     */
    public RealmResultTask<MongoCursor<csci310.team53.easyteamup.data.User>> getAllUsers() {
        return app.getDatabase().users.find(new Document()).sort(new Document("username", 1)).iterator();
    }

}

