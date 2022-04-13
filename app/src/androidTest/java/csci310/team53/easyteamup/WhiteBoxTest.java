package csci310.team53.easyteamup;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.google.android.gms.maps.model.LatLng;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import csci310.team53.easyteamup.activities.CreateEventActivity;
import csci310.team53.easyteamup.activities.LoginActivity;
import csci310.team53.easyteamup.activities.MapsActivity;
import csci310.team53.easyteamup.activities.RegistrationActivity;
import csci310.team53.easyteamup.data.Event;
import csci310.team53.easyteamup.data.Message;
import csci310.team53.easyteamup.util.TimeSlot;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;

/**
 * White box testing for EasyTeamUp
 * @author Thomas Peters
 */
@RunWith(AndroidJUnit4.class)
public class WhiteBoxTest {

    @Rule
    public ActivityTestRule<LoginActivity> loginActivityRule = new ActivityTestRule<>(LoginActivity.class);


    private EasyTeamUp app;


    /**
     * Creates an instance of the application class on startup and
     * signs in to a default user to access the realms database.
     */
    @Before
    public void setup() {
        // Login as tapeters and initialize database
        app = (EasyTeamUp) loginActivityRule.getActivity().getApplication();
        Credentials credentials = Credentials.emailPassword("tapeters", "mypassword");
        app.getRealm().login(credentials);
        app.initializeDatabase(app.getRealm().currentUser());


    }

    /**
     * Checks if all handlers were successfully initialized.
     */
    @Test
    public void applicationHandlerSetup() {
        assertNotNull(app);
        assertNotNull(app.getMessageHandler());
        assertNotNull(app.getDatabase());
        assertNotNull(app.getEventHandler());
        assertNotNull(app.getUserHandler());
        assertNotNull(app.getVotingHandler());
    }

    /**
     * Test if realms login is successful and sets correct user.
     */
    @Test
    public void realmsLogin() {
        User user = app.getRealm().currentUser();
        assertNotNull(user);
        assertTrue(user.isLoggedIn());
        assertEquals(user.getId(), "623d03730e82c57fefa52fb2");
    }

    /**
     * Test if an event can be added and retrieved from a database with the same info.
     */
    @Test
    public void addEventToDatabase() {
        // Create event object in local memory
        ObjectId id = new ObjectId("625611b89ff84e72d1aab7d3");
        Event e = new Event(id, "Greek Life Party", "USC Village",
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

    @Test
    public void addMessageToDatabase() {
        // Create event object in local memory
        ObjectId id = new ObjectId("625611b89ff84e72d1aab7de");
        ObjectId event = new ObjectId("623d03730e82c57fefa52fb2");
        Message m = new Message(id, "625611b89ff84e72d1aab7de", null, "test",event );


        // Insert into database if not already there
        long count = app.getDatabase().messages.count(new Document("_id", id)).get();
        if (count <= 0) {
            app.getDatabase().messages.insertOne(m).get();
        }

        // Retrieve event from database and check values
        Message retrievedMessage = app.getDatabase().messages.findOne(new Document("_id", id)).get();
        assertEquals(m.getId(), retrievedMessage.getId());
        assertEquals(m.getSender(), retrievedMessage.getSender());
        assertEquals(m.getContent(), retrievedMessage.getContent());
        assertEquals(m.getEvent(), retrievedMessage.getEvent());


    }


    @Test
    public void sendNotification() {
        // TODO: Test sending a notification
        assertTrue(true);
    }

    @Test
    public void realmsLogout() {
        User user = app.getRealm().currentUser();
        assertNotNull(user);
        user.logOut();
        assertFalse(user.isLoggedIn());
    }





   @Test
    public void testMapLogicNull(){


       Context context = ApplicationProvider.getApplicationContext();

      try( ActivityScenario<MapsActivity> scenario = ActivityScenario.launch(MapsActivity.class)) {
            scenario.onActivity( activity -> {
                LatLng ans = activity.getLocationFromAddress(context , null);

                assertEquals(ans, null);
            });
       }

   }

    @Test
    public void testMapLogicSuccess(){
        
        Context context = ApplicationProvider.getApplicationContext();

        try( ActivityScenario<MapsActivity> scenario = ActivityScenario.launch(MapsActivity.class)) {
            scenario.onActivity( activity -> {
                LatLng ans = activity.getLocationFromAddress(context , "3607 Trousdale Pkwy, Los Angeles, CA 90089 ");

                LatLng test = new LatLng(34.019963,-118.2861901);
                assertEquals(ans, test);
            });
        }

    }

    @Test
    public void addSlot() {
        Context context = ApplicationProvider.getApplicationContext();
        try (ActivityScenario<CreateEventActivity> scenario = ActivityScenario.launch(CreateEventActivity.class)) {
            scenario.onActivity( activity-> {
                activity.addSlot("11:00 AM", "12:00 PM");

                assertEquals(activity.getTimeSlots().get(0).getStart(), "11:00 AM");
                assertEquals(activity.getTimeSlots().get(0).getEnd(), "12:00 PM");
            });
        }
    }

    @Test
    public void getSlots() {
        Context context = ApplicationProvider.getApplicationContext();
        try (ActivityScenario<CreateEventActivity> scenario = ActivityScenario.launch(CreateEventActivity.class)) {
            scenario.onActivity( activity-> {
                activity.addSlot("11:00 AM", "12:00 PM");
                activity.addSlot("12:00 PM", "1:00 PM");
                activity.addSlot("1:00 PM", "2:00 PM");

                assertEquals(activity.getTimeSlots().size(), 3);
            });
        }
    }

    @Test
    public void registerUser() {
        try (ActivityScenario<RegistrationActivity> scenario = ActivityScenario.launch(RegistrationActivity.class)) {
            scenario.onActivity( activity-> {
                activity.register("unittest", "password");
//                csci310.team53.easyteamup.data.User testUser = app.getDatabase().users.findOne(new Document("username", "tapeters")).get();
//                assertEquals("testuser", testUser.getUsername());

            });
        }
    }


}
