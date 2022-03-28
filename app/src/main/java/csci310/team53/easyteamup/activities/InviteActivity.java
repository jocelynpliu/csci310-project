package csci310.team53.easyteamup.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import csci310.team53.easyteamup.EasyTeamUp;
import csci310.team53.easyteamup.R;
import csci310.team53.easyteamup.data.Event;

/**
 * Shows invite notification details, including an 'accept' and 'deny' button.
 *
 * @author Justin Nakama, Thomas Peters
 */
public class InviteActivity extends AppCompatActivity {

    private Button myEventsButton;
    private Button inboxButton;
    private Button myHostedEventsButton;
    private Button myHomeButton;
    private Button myAcceptButton;
    private Button myDenyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);
        setTitle("Event Invitation ");

        EasyTeamUp app = (EasyTeamUp) this.getApplication();
        String eventID = getIntent().getStringExtra("eventID");
        String senderID = getIntent().getStringExtra("senderID");
        String content = getIntent().getStringExtra("content");

        app.getEventHandler().retrieveEvent(eventID).getAsync(task -> {
            if (task.isSuccess()) {
                Event event = task.get().next();
                ((EditText) findViewById(R.id.eventName)).setText(event.getName());
                ((EditText) findViewById(R.id.eventAddress)).setText(event.getLocation());
                ((EditText) findViewById(R.id.description)).setText(event.getDescription());
                ((EditText) findViewById(R.id.dateText)).setText(event.getDate());
                ((EditText) findViewById(R.id.startTimeText)).setText(event.getStart());
                ((EditText) findViewById(R.id.endTimeText)).setText(event.getEnd());
            } else {
                Log.v("EVENTS", "ERROR: " + task.getError().getErrorMessage());
            }
        });

        //button stuff---------------------------------------
        //go to inbox
        inboxButton = (Button) findViewById(R.id.inboxButton);
        inboxButton.setOnClickListener(v -> {
            Intent intent = new Intent(InviteActivity.this, InboxActivity.class);
            startActivity(intent);
        });

        //go to My hosted Events
        myHostedEventsButton = (Button) findViewById(R.id.hostedEventsButton);
        myHostedEventsButton.setOnClickListener(v -> {
            Intent intent = new Intent(InviteActivity.this, HostedEventsActivity.class);
            startActivity(intent);
        });

        //go to Home
        myHomeButton = (Button) findViewById(R.id.homeButton);
        myHomeButton.setOnClickListener(v -> {
            Intent intent = new Intent(InviteActivity.this, HomeActivity.class);
            startActivity(intent);
        });

        //go to myEvents
        myEventsButton = (Button) findViewById(R.id.myEventsButton);
        myEventsButton.setOnClickListener(v -> {
            Intent intent = new Intent(InviteActivity.this, EventsActivity.class);
            startActivity(intent);
        });

        // when you accept or deny, will pass extra info, return back to InboxActitity class to notify hosts
        myAcceptButton = (Button) findViewById(R.id.acceptButton);
        myAcceptButton.setOnClickListener(v -> {
            // Add this user to list of attendees for specified event
            app.getEventHandler().attendEvent(eventID).getAsync(task -> {
                if (task.isSuccess()) {
                    Intent intent = new Intent(InviteActivity.this, InviteResultActivity.class);
                    intent.putExtra("isAttending", true);
                    intent.putExtra("hostID", task.get().getHost().toString());
                    intent.putExtra("eventID", task.get().getId().toString());
                    startActivity(intent);
                }
            });
        });

        myDenyButton = (Button) findViewById(R.id.denyButton);
        myDenyButton.setOnClickListener(v -> {
            // Add this user to list of attendees for specified event
            app.getEventHandler().denyEvent(eventID).getAsync(task -> {
                if (task.isSuccess()) {
                    Intent intent = new Intent(InviteActivity.this, InviteResultActivity.class);
                    intent.putExtra("isAttending", false);
                    intent.putExtra("hostID", task.get().getHost().toString());
                    intent.putExtra("eventID", task.get().getId().toString());
                    startActivity(intent);
                }
            });
        });
    }
}
