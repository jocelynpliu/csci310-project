package com.example.csci310;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;

/**
 * Initial activity screen, allows user to login or begin registration.
 *
 * @author Thomas Peters, Jocelyn Liu
 */
public class MainActivity extends AppCompatActivity {

    public static final String APP_ID = "easyteamup-fysss";

    private EditText usernameInput;
    private EditText passwordInput;

    /**
     * Login user or redirect to registration form.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usernameInput = (EditText) findViewById(R.id.username);
        passwordInput = (EditText) findViewById(R.id.password);

        Button loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(v -> login());

        TextView registerText = (TextView) findViewById(R.id.create_account);
        registerText.setOnClickListener(view -> openCreateAccountActivity());
    }

    /**
     * Login the user with MongoDB Realm using their email and password.
     */
    public void login() {
        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();
        if (username.isEmpty() || password.isEmpty()) return;

        Realm.init(this);
        App app = new App(new AppConfiguration.Builder(APP_ID).build());

        Credentials credentials = Credentials.emailPassword(username, password);
        app.loginAsync(credentials, result -> {
            if (result.isSuccess()) {
                Log.v("User", "Logged in successfully");
                openHomeActivity();
            } else {
                Log.v("User", "Failed to login");
                // TODO: Provide some error message that login failed and clear EditText
            }
        });
    }

    /**
     * Opens activity to register for new account.
     */
    public void openCreateAccountActivity() {
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }

    /**
     * Opens activity to main app screen.
     */
    public void openHomeActivity(){
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

}