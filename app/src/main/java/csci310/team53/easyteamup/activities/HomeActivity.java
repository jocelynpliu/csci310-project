package csci310.team53.easyteamup.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import csci310.team53.easyteamup.R;

import csci310.team53.easyteamup.activities.adapters.HomeRecyclerAdapter;
import csci310.team53.easyteamup.activities.adapters.RecyclerViewInterface;

public class HomeActivity extends AppCompatActivity implements RecyclerViewInterface {
    private Button homeButton;
    private Button inboxButton;
    private Button myEventsButton;
    private Button myHostButton;

    String s1[];

    RecyclerView homeRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Log.d("----------start", " Home.java");

        //gray out the button to make it look selected and cool
        setTitle("Public Events");
        homeButton = (Button) findViewById(R.id.homeButton);
        homeButton.setAlpha(.5f);

        //just for testing purposes
        //later, get all event objects titles in a list, sorted order
        s1 = new String[]{"event 1", "event 2", "event3", "event4",
                "event 5", "event 6", "event 7", "event 8", "event 9",
        "event 10", "event 11"};

        //display list stuff ---------------------------------------------
        //recycler and adapter needed to display a dynamic list on the screen
       homeRecyclerView = findViewById(R.id.homeRecyclerView);
       homeRecyclerView.setNestedScrollingEnabled(false);

        homeRecyclerView.setHasFixedSize(true);

        //specific array of data goes into this adapter
        HomeRecyclerAdapter myAdapter = new HomeRecyclerAdapter (this, s1, this);

        homeRecyclerView.setAdapter(myAdapter);
        homeRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        //-----------------------------------------------------------------
        //go to inbox
        inboxButton = (Button) findViewById(R.id.inboxButton);
        inboxButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(HomeActivity.this, InboxActivity.class);
                startActivity(intent);
            }
        });

        //go to My Events
        myEventsButton = (Button) findViewById(R.id.myEventsButton);
        myEventsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(HomeActivity.this, EventsActivity.class);
                startActivity(intent);
            }
        });


        //go to My Hosted Events
        myHostButton= (Button) findViewById(R.id.hostedEventsButton);
        myHostButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(HomeActivity.this, HostedEventsActivity.class);
                startActivity(intent);
            }
        });


    }

    //this is listens to clicks on each row, (each public event displayed)
    // should open specific event info after click
    // position is index of the event in the list, could pass in more info maybe?
    @Override
    public void onItemClick(int position) {
        Log.d("---INDEX: " +  String.valueOf(position), "Clicked!!");

        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }
}
