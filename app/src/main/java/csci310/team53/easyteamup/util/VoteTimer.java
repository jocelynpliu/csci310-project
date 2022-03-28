package csci310.team53.easyteamup.util;

import org.bson.Document;
import org.bson.types.ObjectId;

import csci310.team53.easyteamup.EasyTeamUp;

/**
 * Finalizes vote data on timed execution.
 *
 * @author Thomas Peters
 */
public class VoteTimer implements Runnable {

    private final EasyTeamUp app;
    private final String eventID;

    public VoteTimer(EasyTeamUp app, String eventID) {
        this.app = app;
        this.eventID = eventID;
    }

    @Override
    public void run() {

        // TODO: Get winner vote slot and update event in databse
        Document updateQuery = new Document();

        Document findQuery = new Document("_id", new ObjectId(eventID));
        app.getDatabase().events.findOneAndUpdate(findQuery, updateQuery).getAsync(task -> {
            if (task.isSuccess()) {
                // TODO: Notify users of set time
            }
        });
    }
}
