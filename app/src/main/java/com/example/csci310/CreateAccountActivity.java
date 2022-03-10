package com.example.csci310;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class CreateAccountActivity extends AppCompatActivity {
    Button uploadButton;
    Button registerButton;
    ImageView previewImage;
    UserHandler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ImageView imageView = (ImageView) findViewById(R.id.preview_image);
//        imageView.setImageResource(R.drawable.);
        setContentView(R.layout.activity_createacct);

        uploadButton = (Button) findViewById(R.id.upload_button);
        registerButton = (Button) findViewById(R.id.register_button);
        previewImage = (ImageView) findViewById(R.id.preview_image);

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { uploadPicture(); }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler = new UserHandler();
                EditText email = (EditText) findViewById(R.id.email);
                EditText username = (EditText) findViewById(R.id.username);
                EditText password = (EditText) findViewById(R.id.password);
                if (isEmpty(email) || isEmpty(username) || (isEmpty(password))) {
                    // write code to ALERT user fields cannot be empty!
                }
                else {
                    // registration diverted to UserHandler
                    boolean registered = handler.register(email.getText().toString(), username.getText().toString(), password.getText().toString());
                    if (registered) {
                        // console messages
                        String TAG = "REGISTER";
                        Log.v(TAG, "registered!");

                        // after a successful registration, redirect user to login
                        Intent intent = new Intent(CreateAccountActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    // launches user's gallery to select an image to upload
    private void uploadPicture() {
        activityLauncher.launch("image/*");
    }

    // to check if the EditText is empty
    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    // sets the display image to user's uploaded image
    ActivityResultLauncher<String> activityLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    previewImage.setImageURI(uri);
                }
            });

}
