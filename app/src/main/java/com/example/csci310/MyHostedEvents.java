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

public class MyHostedEvents extends AppCompatActivity implements RecyclerViewInterface {
    private Button myHostedEventsButton;
    String s1[];

    RecyclerView myHostedEventsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_hosted_events);

        //gray out the button to make it look selected and cool
        setTitle("My Hosting Events");
        myHostedEventsButton = (Button) findViewById(R.id.hostedEventsButton);
        myHostedEventsButton.setAlpha(.5f);

        //just for testing purposes
        //later, get all event objects titles in a list, sorted order
        s1 = new String[]{"host event 1", " host event 2", "host event  3", "host event  4",
                "host event  5", "host event 6", "host event  7",
                "host event  8", "host event  9",
                "host event  10", "host event  11"};

        //recycler and adapter needed to display a dynamic list on the screen
        myHostedEventsRecyclerView = findViewById(R.id.myHostedEventsRecyclerView);
        myHostedEventsRecyclerView.setNestedScrollingEnabled(false);

        myHostedEventsRecyclerView.setHasFixedSize(true);

        //specific array of data goes into this adapter
        InboxRecyclerAdapter myAdapter = new InboxRecyclerAdapter(this, s1, this);

        myHostedEventsRecyclerView.setAdapter(myAdapter);
        myHostedEventsRecyclerView.setLayoutManager(new LinearLayoutManager(this));


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
