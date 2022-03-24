package csci310.team53.easyteamup.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import org.bson.types.ObjectId;

import java.util.concurrent.Callable;

import csci310.team53.easyteamup.EasyTeamUp;
import csci310.team53.easyteamup.R;
import csci310.team53.easyteamup.data.User;

/**
 * Registration form to create new account.
 *
 * @author Thomas Peters, Jocelyn Liu
 */
public class RegistrationActivity extends AppCompatActivity {

    private Button uploadButton;
    private Button registerButton;
    private ImageView previewImage;
    private EditText usernameInput;
    private EditText passwordInput;

    // sets the display image to user's uploaded image
    private final ActivityResultLauncher<String> activityLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    previewImage.setImageURI(uri);
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createacct);

        uploadButton = (Button) findViewById(R.id.upload_button);
        registerButton = (Button) findViewById(R.id.register_button);
        previewImage = (ImageView) findViewById(R.id.preview_image);
        usernameInput = (EditText) findViewById(R.id.username);
        passwordInput = (EditText) findViewById(R.id.password);

        uploadButton.setOnClickListener(view -> uploadPicture());

        registerButton.setOnClickListener(view -> {
            String username = usernameInput.getText().toString();
            String password = passwordInput.getText().toString();
            if (username.isEmpty() || password.isEmpty()) {
                // TODO: Display error message saying "must fill in all fields" or something.
                return;
            }
            if (username.length() < 6 || password.length() < 6) {
                // TODO: Display error message saying "must be at least 6 characters" or something.
                return;
            }
            register(username, password);
        });
    }

    /**
     * Registers a new account with a username and password pair.
     *
     * @param username The input string username.
     * @param password The inout string password.
     */
    private void register(String username, String password) {
        EasyTeamUp app = (EasyTeamUp) this.getApplication();
        Callable<Void> fail = () -> {
            //TODO: Provide some error message that registration failed.
            return null;
        };
        Callable<Void> success = () -> {
            Callable<Void> successLogin = () -> {
                io.realm.mongodb.User userData = app.getRealm().currentUser();
                app.initializeDatabase(userData);
                User user = new User(new ObjectId(userData.getId()), username, password);
                app.getDatabase().users.insertOne(user).getAsync(task -> {
                    if (task.isSuccess()) {
                        openHomeActivity();
                    }
                });
                return null;
            };
            app.getUserHandler().login(username, password, successLogin, fail);
            return null;
        };
        app.getUserHandler().register(username, password, success, fail);
    }

    /**
     * Launches user's gallery to select an image to upload
     */
    private void uploadPicture() {
        activityLauncher.launch("image/*");
    }

    /**
     * Opens activity to main app screen.
     */
    private void openHomeActivity(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
