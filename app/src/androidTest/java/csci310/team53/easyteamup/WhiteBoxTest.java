package csci310.team53.easyteamup;

import static org.junit.Assert.assertEquals;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import csci310.team53.easyteamup.activities.LoginActivity;
import csci310.team53.easyteamup.data.Event;
import io.realm.mongodb.Credentials;

@RunWith(AndroidJUnit4.class)
public class WhiteBoxTest {

    @Rule
    public ActivityTestRule<LoginActivity> loginActivityRule = new ActivityTestRule<>(LoginActivity.class);

    private EasyTeamUp app;

    @Before
    public void setup() {
        // Login as tapeters and initialize database
        app = (EasyTeamUp) loginActivityRule.getActivity().getApplication();
        Credentials credentials = Credentials.emailPassword("tapeters", "mypassword");
        app.getRealm().login(credentials);
        app.initializeDatabase(app.getRealm().currentUser());
    }

    @Test
    public void addEventToDatabase() {
        // Create event object in local memory
        ObjectId id = new ObjectId("625611b89ff84e72d1aab7de");
        Event e = new Event(id, "Greek Life Protest", "USC Village",
                "protest", "623d03730e82c57fefa52fb2", false, "11/02/2022", "11:00 AM",
                "12:00 PM", new ArrayList<>(), new ArrayList<>());

        // Insert into database if not already there
        long count = app.getDatabase().events.count(new Document("_id", id)).get();
        if (count <= 0) {
            app.getDatabase().events.insertOne(e).get();
        }

        // Retrieve event from database and check values
        Event retrievedEvent = app.getDatabase().events.findOne(new Document("_id", id)).get();
        assertEquals(e.getName(), retrievedEvent.getName());
        assertEquals(e.getDescription(), retrievedEvent.getDescription());
        assertEquals(e.getLocation(), retrievedEvent.getLocation());
        assertEquals(e.getHost(), retrievedEvent.getHost());
        assertEquals(e.isPrivate(), retrievedEvent.isPrivate());
        assertEquals(e.getDate(), retrievedEvent.getDate());
        assertEquals(e.getStart(), retrievedEvent.getStart());
        assertEquals(e.getEnd(), retrievedEvent.getEnd());
        assertEquals(e.getInvitees(), retrievedEvent.getInvitees());
        assertEquals(e.getTimeSlots(), retrievedEvent.getTimeSlots());
    }
}
