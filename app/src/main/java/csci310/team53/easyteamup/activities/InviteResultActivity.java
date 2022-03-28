package csci310.team53.easyteamup.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import org.bson.types.ObjectId;

import java.util.Arrays;
import java.util.List;

import csci310.team53.easyteamup.EasyTeamUp;
import csci310.team53.easyteamup.R;
import csci310.team53.easyteamup.data.Event;

/**
 * Displays information after accepting or denying an invitation.
 *
 * @author Thomas Peters
 */
public class InviteResultActivity extends AppCompatActivity {

    private EasyTeamUp app;

    private Button myEventsButton;
    private Button myHostedEventsButton;
    private Button myHomeButton;
    private Button inboxButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        app = (EasyTeamUp) this.getApplication();

        // Get data from previous intent
        Intent prevIntent = getIntent();
        boolean isAttending = prevIntent.getBooleanExtra("isAttending", false);
        String hostID = prevIntent.getStringExtra("hostID");
        String eventID = prevIntent.getStringExtra("eventID");

        // Notify host

        if(hostID.equals("NOTIFYattendees")){
            app.getEventHandler().retrieveEvent(eventID).getAsync(task -> {
                List<ObjectId> attendees;
                if (task.isSuccess()) {
                    Event event = task.get().next();
                    attendees = event.getAttendees();
                    app.getMessageHandler().sendMessage(attendees, "The host has edited an event!");
                } else {
                    Log.v("EVENTS", "ERROR: " + task.getError().getErrorMessage());
                }
            });


        }
        else if (isAttending) {
            app.getMessageHandler().sendMessage(Arrays.asList(new ObjectId(hostID)), "Someone joined your event!");
//
        } else {
            Log.d("NOT ", "ATTENDING------------------");
            app.getMessageHandler().sendMessage(Arrays.asList(new ObjectId(hostID)), "Someone denied an invite to your event!");
//
        }



        // final LayoutInflater factory = getLayoutInflater();
        // final View textEntryView = factory.inflate(R.layout.activity_confirm, null);

        //go to inbox
        inboxButton = (Button) findViewById(R.id.inboxButton);
        inboxButton.setOnClickListener(v -> {
            Intent intent = new Intent(InviteResultActivity.this, InboxActivity.class);
            startActivity(intent);
        });

        //go to My hosted Events
        myHostedEventsButton = (Button) findViewById(R.id.hostedEventsButton);
        myHostedEventsButton.setOnClickListener(v -> {
            Intent intent = new Intent(InviteResultActivity.this, HostedEventsActivity.class);
            startActivity(intent);
        });

        // Navigate to My Events
        myEventsButton = (Button) findViewById(R.id.myEventsButton);
        myEventsButton.setOnClickListener(v -> {
            Intent intent = new Intent(InviteResultActivity.this, EventsActivity.class);
            startActivity(intent);
        });

        //go to Home
        myHomeButton= (Button) findViewById(R.id.homeButton);
        myHomeButton.setOnClickListener(v -> {
            Intent intent = new Intent(InviteResultActivity.this, HomeActivity.class);
            startActivity(intent);
        });
    }
}
