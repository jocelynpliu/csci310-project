package com.example.csci310;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class Home extends AppCompatActivity {
    private Button homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        homeButton = (Button) findViewById(R.id.homeButton);
        homeButton.setAlpha(.5f);
    }
}
