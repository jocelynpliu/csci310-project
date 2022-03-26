package csci310.team53.easyteamup.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

import csci310.team53.easyteamup.EasyTeamUp;
import csci310.team53.easyteamup.R;

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

        // Notify host
        if (isAttending) {
            app.getMessageHandler().sendMessage(Arrays.asList(hostID), "Someone joined your event!");
        } else {
            app.getMessageHandler().sendMessage(Arrays.asList(hostID), "Someone denied an invite to your event!");
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
