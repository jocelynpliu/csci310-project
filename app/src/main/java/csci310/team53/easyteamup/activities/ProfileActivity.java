package csci310.team53.easyteamup.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import csci310.team53.easyteamup.EasyTeamUp;
import csci310.team53.easyteamup.R;

public class ProfileActivity extends AppCompatActivity {

    private EasyTeamUp app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        app = ((EasyTeamUp) this.getApplication());

        TextView usernameDisplay = (TextView) findViewById(R.id.username);
        usernameDisplay.setText("Tapeters");

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
