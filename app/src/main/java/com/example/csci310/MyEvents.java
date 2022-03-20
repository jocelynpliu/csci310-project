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

public class MyEvents extends AppCompatActivity implements RecyclerViewInterface {
    private Button myEventsButton;
    private Button myHostedEventsButton;
    private Button myHomeButton;
    private Button inboxButton;
    String s1[];

    RecyclerView myEventsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_events);

        //gray out the button to make it look selected and cool
        setTitle("Attending Events");
        myEventsButton = (Button) findViewById(R.id.myEventsButton);
        myEventsButton.setAlpha(.5f);

        //just for testing purposes
        //later, get all event objects titles in a list, sorted order
        s1 = new String[]{"attend. event 1", " attend. event 2", "attend. event  3", "attend. event  4",
                "attend. event  5", "attend. event 6", "attend. event  7", "attend. event  8", "attend. event  9",
                "attend. event  10", "attend. event  11"};

        //recycler and adapter needed to display a dynamic list on the screen
        myEventsRecyclerView = findViewById(R.id.myEventsRecyclerView);
        myEventsRecyclerView.setNestedScrollingEnabled(false);

        myEventsRecyclerView.setHasFixedSize(true);

        //specific array of data goes into this adapter
        MyEventsRecyclerAdapter myAdapter = new MyEventsRecyclerAdapter(this, s1, this);

        myEventsRecyclerView.setAdapter(myAdapter);
        myEventsRecyclerView.setLayoutManager(new LinearLayoutManager(this));



        // button stuff---------------------------
        //go to inbox
        inboxButton = (Button) findViewById(R.id.inboxButton);
        inboxButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MyEvents.this, Inbox.class);
                startActivity(intent);
            }
        });

        //go to My hosted Events
        myHostedEventsButton = (Button) findViewById(R.id.hostedEventsButton);
        myEventsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MyEvents.this, MyHostedEvents.class);
                startActivity(intent);
            }
        });


        //go to Home
        myHomeButton= (Button) findViewById(R.id.homeButton);
        myHomeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MyEvents.this, Home.class);
                startActivity(intent);
            }
        });


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
