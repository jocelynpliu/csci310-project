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

import csci310.team53.easyteamup.activities.adapters.HostedEventsRecyclerAdapter;
import csci310.team53.easyteamup.activities.adapters.RecyclerViewInterface;

public class HostedEventsActivity extends AppCompatActivity implements RecyclerViewInterface {
    private Button myHostedEventsButton;
    private Button inboxButton;
    private Button myEventsButton;
    private Button myHomeButton;

    String s1[];
    String s2[];
    String s3[];

    RecyclerView myHostedEventsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_hosted_events);

        //gray out the button to make it look selected and cool
        setTitle("My Hosted Events");
        myHostedEventsButton = (Button) findViewById(R.id.hostedEventsButton);
        myHostedEventsButton.setAlpha(.5f);

        //just for testing purposes
        //later, get all event objects titles in a list, sorted order
        s1 = new String[]{"host event 1", " host event 2", "host event  3", "host event  4",
                "host event  5", "host event 6", "host event  7",
                "host event  8", "host event  9",
                "host event  10", "host event  11"};

        s2 = new String []{"host1", "host2", "host3", "host4", "host5", "host6", "host7"
                ,"host8", "host9", "host10", "host11"};

        s3 = new String []{"date1", "date2", "date3", "date4", "date5", "date6", "date7"
                , "date8", "date9", "date10", "date11"};


        //recycler and adapter needed to display a dynamic list on the screen
        myHostedEventsRecyclerView = findViewById(R.id.myHostedEventsRecyclerView);
        myHostedEventsRecyclerView.setNestedScrollingEnabled(false);

        myHostedEventsRecyclerView.setHasFixedSize(true);

        //specific array of data goes into this adapter
        HostedEventsRecyclerAdapter myAdapter = new HostedEventsRecyclerAdapter(this, s1, s2, s3, this);

        myHostedEventsRecyclerView.setAdapter(myAdapter);
        myHostedEventsRecyclerView.setLayoutManager(new LinearLayoutManager(this));



        //go to inbox
        inboxButton = (Button) findViewById(R.id.inboxButton);
        inboxButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(HostedEventsActivity.this, InboxActivity.class);
                startActivity(intent);
            }
        });

        //go to My Events
        myEventsButton = (Button) findViewById(R.id.myEventsButton);
        myEventsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(HostedEventsActivity.this, EventsActivity.class);
                startActivity(intent);
            }
        });


        //go to Home
        myHomeButton= (Button) findViewById(R.id.homeButton);
        myHomeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(HostedEventsActivity.this, HomeActivity.class);
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

        Intent intent = new Intent(this, EventDetailsActivity.class);
        startActivity(intent);
    }
}
