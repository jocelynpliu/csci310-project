package com.example.csci310;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toolbar;

public class Inbox extends AppCompatActivity implements RecyclerViewInterface {
    private Button inboxButton;
    String s1[];

    RecyclerView inboxRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);

        Log.d("----------start", " Home.java");

        //gray out the button to make it look selected and cool
        setTitle("Inbox");

        inboxButton = (Button) findViewById(R.id.inboxButton);
        inboxButton.setAlpha(.5f);

        //just for testing purposes
        //later, get all event objects titles in a list, sorted order
        s1 = new String[]{"noti 1", "noti 2", "noti 3", "noti 4",
                "noti  5", "noti  6", "noti  7", "noti  8", "noti  9",
                "noti  10", "noti  11"};

        //recycler and adapter needed to display a dynamic list on the screen
        inboxRecyclerView = findViewById(R.id.inboxRecyclerView);
        inboxRecyclerView.setNestedScrollingEnabled(false);

        inboxRecyclerView.setHasFixedSize(true);

        //specific array of data goes into this adapter
        InboxRecyclerAdapter myAdapter = new InboxRecyclerAdapter(this, s1, this);

        inboxRecyclerView.setAdapter(myAdapter);
        inboxRecyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    //this is listens to clicks on each row, (each notification received displayed)
    // should open specific event info about notification after click
    // position is index of the notification in the list
    @Override
    public void onItemClick(int position) {
        Log.d("---INDEX: " +  String.valueOf(position), "Clicked!!");

        Intent intent = new Intent(this, Event.class);
        startActivity(intent);
    }
}
