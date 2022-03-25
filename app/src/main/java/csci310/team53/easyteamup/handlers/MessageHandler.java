package csci310.team53.easyteamup.handlers;

import android.util.Log;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.List;

import csci310.team53.easyteamup.EasyTeamUp;
import csci310.team53.easyteamup.data.Message;
import io.realm.mongodb.RealmResultTask;
import io.realm.mongodb.mongo.iterable.MongoCursor;

/**
 * Handles CRUD for Event objects in Database.
 *
 * @author Thomas Peters
 */
public class MessageHandler {

    private final EasyTeamUp app;

    /**
     * Constructor with reference to application class.
     *
     * @param app reference to main application class.
     */
    public MessageHandler(EasyTeamUp app) {
        this.app = app;
    }

    /**
     * Creates a new message and adds it to the receivers' profile.
     *
     * @param receivers list of user IDs of the users receiving this message.
     * @param content The content body of the message.
     */
    public void sendMessage(List<String> receivers, String content) {
        ObjectId id = new ObjectId();
        String sender = app.getRealm().currentUser().getId();
        Message message = new Message(id, sender, receivers, content);
        app.getDatabase().messages.insertOne(message).getAsync(task -> {
            if (task.isSuccess()) {
                for (String userID : receivers) {
                    Document findQuery = new Document("_id", new ObjectId(userID));
                    Document updateQuery = new Document("$push", new Document("messages", id));
                    app.getDatabase().users.findOneAndUpdate(findQuery, updateQuery).getAsync(task2 -> {
                        if (!task2.isSuccess()) {
                            Log.v("Message", "ERROR: " + task2.getError().getErrorMessage());
                        }
                    });
                }
            }
        });
    }

    /**
     * Retrieves all messages sent to the current user.
     *
     * @return an async task that you must run .getAsync() on.
     */
    public RealmResultTask<MongoCursor<Message>> retrieveMessages() {
        // TODO:IMPLEMENT
        return null;
    }

    public void clearInbox() {
        // TODO:IMPLEMENT
    }
}
