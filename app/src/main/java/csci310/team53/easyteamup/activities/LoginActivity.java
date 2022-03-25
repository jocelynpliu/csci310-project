package csci310.team53.easyteamup.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import csci310.team53.easyteamup.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

import csci310.team53.easyteamup.EasyTeamUp;
import io.realm.mongodb.App;

/**
 * Initial activity screen, allows user to login or begin registration.
 *
 * @author Thomas Peters, Jocelyn Liu
 */
public class LoginActivity extends AppCompatActivity {

    private EditText usernameInput;
    private EditText passwordInput;

    /**
     * Activity view to login or redirect to registration form.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameInput = (EditText) findViewById(R.id.username);
        passwordInput = (EditText) findViewById(R.id.password);

        TextView registerText = (TextView) findViewById(R.id.create_account);
        registerText.setOnClickListener(view -> openCreateAccountActivity());

        Button loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(v -> {
            String username = usernameInput.getText().toString();
            String password = passwordInput.getText().toString();
            if (username.isEmpty() || password.isEmpty()) return;
            login(username, password);
        });
    }

    /**
     * Attempt to login user with specified username and password
     * @param username String username for this account.
     * @param password String password for this account.
     */
    private void login(String username, String password) {
        Callable<Void> success = () -> {
            Log.v("User", "Logged in successfully");
            EasyTeamUp app = ((EasyTeamUp) this.getApplication());
            app.initializeDatabase(app.getUserHandler().getUser());
            openHomeActivity();
            return null;
        };
        Callable<Void> fail = () -> {
            Log.v("User", "Failed to login");
            //TODO: Provide some error message that login failed and clear EditText
            return null;
        };
        ((EasyTeamUp) this.getApplication()).getUserHandler().login(username, password, success, fail);
    }

    /**
     * Opens activity to register for new account.
     */
    private void openCreateAccountActivity() {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

    /**
     * Opens activity to main app screen.
     */
    private void openHomeActivity(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

}