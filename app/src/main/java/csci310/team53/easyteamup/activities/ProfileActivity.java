package csci310.team53.easyteamup.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.bson.Document;
import org.bson.types.ObjectId;

import csci310.team53.easyteamup.EasyTeamUp;
import csci310.team53.easyteamup.R;
import csci310.team53.easyteamup.data.User;

public class ProfileActivity extends AppCompatActivity {

    private EasyTeamUp app;

    private Button homeButton;
    private Button inboxButton;
    private Button myEventsButton;
    private Button myHostButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        app = ((EasyTeamUp) this.getApplication());

        Document userQuery = new Document("_id", new ObjectId(app.getRealm().currentUser().getId()));
        app.getDatabase().users.find(userQuery).iterator().getAsync(task -> {
            if (task.isSuccess()) {
                User user = task.get().next();
                TextView usernameDisplay = (TextView) findViewById(R.id.username);
                usernameDisplay.setText(user.getUsername());
            }
        });

        Button editProfileButton = (Button) findViewById(R.id.edit_button);
        editProfileButton.setOnClickListener(view -> {
            Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
            startActivity(intent);
        });

        Button logoutButton = (Button) findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(view -> {
            app.getUserHandler().logout(ProfileActivity.this);
        });



        // Navigate to My Events
        myEventsButton = (Button) findViewById(R.id.myEventsButton);
        myEventsButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, EventsActivity.class);
            startActivity(intent);
        });

        // Navigate to My Hosted Events
        myHostButton= (Button) findViewById(R.id.hostedEventsButton);
        myHostButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, HostedEventsActivity.class);
            startActivity(intent);
        });

        // Navigate to inbox
        inboxButton = (Button) findViewById(R.id.inboxButton);
        inboxButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, InboxActivity.class);
            startActivity(intent);
        });

        // Navigate to Home
        homeButton = (Button) findViewById(R.id.homeButton);
        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, EventsActivity.class);
            startActivity(intent);
        });




    }
}
