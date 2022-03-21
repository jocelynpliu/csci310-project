package com.example.csci310;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Registration form to create new account.
 *
 * @author Thomas Peters, Jocelyn Liu
 */
public class RegistrationActivity extends AppCompatActivity {

    private Button uploadButton;
    private Button registerButton;
    private ImageView previewImage;
    private UserHandler handler;

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

        uploadButton.setOnClickListener(view -> uploadPicture());

        registerButton.setOnClickListener(view -> {
            /**
            handler = new UserHandler();
            EditText email = (EditText) findViewById(R.id.eventName);
            EditText username = (EditText) findViewById(R.id.eventLocation);
            EditText password = (EditText) findViewById(R.id.password);
            if (isEmpty(email) || isEmpty(username) || (isEmpty(password))) {
                // write code to ALERT user fields cannot be empty!
            }
            else {
                // registration diverted to UserHandler
                boolean registered = handler.register(username.getText().toString(), password.getText().toString());
                if (registered) {
                    // console messages
                    String TAG = "REGISTER";
                    Log.v(TAG, "registered!");

                    // after a successful registration, redirect user to login
                    Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
             **/
        });
    }

    /**
     * Registers a new account with a username and password pair.
     */
    private void registerUser() {

    }

    /**
     * Launches user's gallery to select an image to upload
     */
    private void uploadPicture() {
        activityLauncher.launch("image/*");
    }

    /**
     * Checks if the EditText is empty (has no text).
     * @param etText the EditText object to check.
     * @return true if empty, otherwise false.
     */
    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }
}
