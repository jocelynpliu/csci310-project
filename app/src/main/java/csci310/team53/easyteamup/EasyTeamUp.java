package csci310.team53.easyteamup;

import android.app.Application;

import csci310.team53.easyteamup.handlers.DatabaseHandler;
import csci310.team53.easyteamup.handlers.EventHandler;
import csci310.team53.easyteamup.handlers.MessageHandler;
import csci310.team53.easyteamup.handlers.UserHandler;
import csci310.team53.easyteamup.handlers.VotingHandler;
import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.User;

/**
 * The main application class for EasyTeamUp.
 * Initializes all handlers and database for use in activities.
 *
 * @author Thomas Peters
 */
public class EasyTeamUp extends Application {

    private App realm;
    private UserHandler userHandler;
    private EventHandler eventHandler;
    private MessageHandler messageHandler;
    private DatabaseHandler databaseHandler;
    private VotingHandler votingHandler;

    /**
     * Initializes all necessary handlers and database management.
     * Runs on application startup before any activity views.
     */
    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize MongoDB Realm Database
        Realm.init(this);
        realm = new App(new AppConfiguration.Builder("easyteamup-fysss").build());

        // Initialize handlers
        userHandler = new UserHandler(this);
        eventHandler = new EventHandler(this);
        messageHandler = new MessageHandler(this);
        votingHandler = new VotingHandler(this);
    }

    /**
     * Getter for MongoDB Realm App.
     * @return realm
     */
    public App getRealm() {
        return realm;
    }

    /**
     * Getter for database handler instance.
     * @return database handler (or NULL if user is not logged in)
     */
    public DatabaseHandler getDatabase() {
        return databaseHandler;
    }

    /**
     * Sets up the database with authenticated user.
     * @param user Authenticated user from loginActivity.
     */
    public void initializeDatabase(User user) {
        if (databaseHandler == null) {
            databaseHandler = new DatabaseHandler(user);
        }
    }

    /**
     * Getter for user handler instance.
     * @return user handler
     */
    public UserHandler getUserHandler() {
        return userHandler;
    }

    /**
     * Getter for event handler instance.
     * @return event handler
     */
    public EventHandler getEventHandler() {
        return eventHandler;
    }

    /**
     * Getter for message handler instance.
     * @return message handler
     */
    public MessageHandler getMessageHandler() {
        return messageHandler;
    }

    /**
     * Getter for voting handler instance.
     * @return voting handler
     */
    public VotingHandler getVotingHandler() {
        return votingHandler;
    }
}
