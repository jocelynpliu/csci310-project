package csci310.team53.easyteamup.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import csci310.team53.easyteamup.R;

public class EventDetailsActivity extends AppCompatActivity {

    private Button myEventsButton;
    private Button inboxButton;
    private Button myHostedEventsButton;
    private Button myHomeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent incomingIntent = getIntent();
        String cameFrom = incomingIntent.getStringExtra("from");

        setContentView(R.layout.activity_event);

        if(cameFrom != null) {
            if (cameFrom.equals("hosted")) {
                setContentView(R.layout.activity_myeventdetails);
            }
        }



        setTitle("A single event ");

        //button stuff---------------------------------------
        //go to inbox
        inboxButton = (Button) findViewById(R.id.inboxButton);
        inboxButton.setOnClickListener(v -> {
            Intent intent = new Intent(EventDetailsActivity.this, InboxActivity.class);
            startActivity(intent);
        });

        //go to My hosted Events
        myHostedEventsButton = (Button) findViewById(R.id.hostedEventsButton);
        myHostedEventsButton.setOnClickListener(v -> {
            Intent intent = new Intent(EventDetailsActivity.this, HostedEventsActivity.class);
            startActivity(intent);
        });


        //go to Home
        myHomeButton= (Button) findViewById(R.id.homeButton);
        myHomeButton.setOnClickListener(v -> {
            Intent intent = new Intent(EventDetailsActivity.this, HomeActivity.class);
            startActivity(intent);
        });

        //go to myEvents
        myEventsButton= (Button) findViewById(R.id.myEventsButton);
        myEventsButton.setOnClickListener(v -> {
            Intent intent = new Intent(EventDetailsActivity.this, EventsActivity.class);
            startActivity(intent);
        });
    }
}
