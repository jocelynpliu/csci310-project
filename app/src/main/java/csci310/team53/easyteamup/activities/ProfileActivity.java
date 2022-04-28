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
            Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
            startActivity(intent);
        });

        Button logoutButton = (Button) findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(view -> {
            app.getUserHandler().logout(ProfileActivity.this);
        });
    }
}
