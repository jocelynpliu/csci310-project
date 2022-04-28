package csci310.team53.easyteamup.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import csci310.team53.easyteamup.EasyTeamUp;
import csci310.team53.easyteamup.R;

public class EditProfileActivity extends AppCompatActivity {

    private EasyTeamUp app;
    private Button homeButton;
    private Button inboxButton;
    private Button myEventsButton;
    private Button myHostButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_profile);

        app = ((EasyTeamUp) this.getApplication());




        // Navigate to My Events
        myEventsButton = (Button) findViewById(R.id.myEventsButton);
        myEventsButton.setOnClickListener(v -> {
            Intent intent = new Intent(EditProfileActivity.this, EventsActivity.class);
            startActivity(intent);
        });

        // Navigate to My Hosted Events
        myHostButton= (Button) findViewById(R.id.hostedEventsButton);
        myHostButton.setOnClickListener(v -> {
            Intent intent = new Intent(EditProfileActivity.this, HostedEventsActivity.class);
            startActivity(intent);
        });

        // Navigate to inbox
        inboxButton = (Button) findViewById(R.id.inboxButton);
        inboxButton.setOnClickListener(v -> {
            Intent intent = new Intent(EditProfileActivity.this, InboxActivity.class);
            startActivity(intent);
        });

        // Navigate to Home
        homeButton = (Button) findViewById(R.id.homeButton);
        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(EditProfileActivity.this, EventsActivity.class);
            startActivity(intent);
        });



    }
}
