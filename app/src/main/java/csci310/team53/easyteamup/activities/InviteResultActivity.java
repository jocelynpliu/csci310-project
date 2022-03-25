package csci310.team53.easyteamup.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import csci310.team53.easyteamup.R;
import csci310.team53.easyteamup.handlers.MessageHandler;

/**
 * Displays information after accepting or denying an invitation.
 *
 * @author Thomas Peters
 */
public class InviteResultActivity extends AppCompatActivity {

    private Button myEventsButton;
    private Button myHostedEventsButton;
    private Button myHomeButton;
    private Button inboxButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        // Get data from previous intent
        Intent prevIntent = getIntent();
        boolean isAttending = prevIntent.getBooleanExtra("isAttending", false);
        Log.d("TEST", "Is Attending: " + isAttending);

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
