package com.example.csci310;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDateTime;
import java.util.List;

public class Event  extends AppCompatActivity {
    private String name;
    private String location;
    private String description;
    private LocalDateTime start;
    private LocalDateTime end;
    private String host;
    private String id;
    private boolean isPrivate;
    private List<String> invitees;

    private Button myEventsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        setTitle("A single event ");

        myEventsButton = (Button) findViewById(R.id.myEventsButton);
        myEventsButton.setAlpha(.5f);

    }

    public void inviteUser(String username) {
        invitees.add(username);
    }

    public void uninviteUser(String username) {
        // IMPLEMENT
    }
}
