package csci310.team53.easyteamup.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import csci310.team53.easyteamup.EasyTeamUp;
import csci310.team53.easyteamup.R;
import csci310.team53.easyteamup.activities.adapters.EventsRecyclerAdapter;
import csci310.team53.easyteamup.activities.adapters.RecyclerViewInterface;
import csci310.team53.easyteamup.data.Event;

/**
 * The main home screen, displaying public events and various navigation buttons.
 *
 * @author Thomas Peters, Justin Nakama
 */
public class HomeActivity extends AppCompatActivity implements RecyclerViewInterface {

    private EasyTeamUp app;
    private RecyclerView homeRecyclerView;
    private EventsRecyclerAdapter myAdapter;

    private Button homeButton;
    private Button inboxButton;
    private Button myEventsButton;
    private Button myHostButton;
    private Button createEventButton;
    private Button mapButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        app = ((EasyTeamUp) this.getApplication());

        setTitle("Public Events");
        homeButton = (Button) findViewById(R.id.homeButton);
        homeButton.setAlpha(.5f);

        // Recycler and adapter needed to display a dynamic list on the screen
        homeRecyclerView = findViewById(R.id.homeRecyclerView);
        homeRecyclerView.setNestedScrollingEnabled(false);
        homeRecyclerView.setHasFixedSize(true);

        // Retrieve public events from database and display from adapter.
        app.getEventHandler().retrievePublicEvents().getAsync(task -> {
            if (task.isSuccess()) {
                myAdapter = new EventsRecyclerAdapter(app, this, task.get(), this);
                homeRecyclerView.setAdapter(myAdapter);
                homeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            }
        });

        // Navigate to inbox
        inboxButton = (Button) findViewById(R.id.inboxButton);
        inboxButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, InboxActivity.class);
            startActivity(intent);
        });

        // Navigate to My Events
        myEventsButton = (Button) findViewById(R.id.myEventsButton);
        myEventsButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, EventsActivity.class);
            startActivity(intent);
        });

        // Navigate to My Hosted Events
        myHostButton= (Button) findViewById(R.id.hostedEventsButton);
        myHostButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, HostedEventsActivity.class);
            startActivity(intent);
        });

        // Navigates to create event activity.
        createEventButton = (Button) findViewById(R.id.createEventButton);
        createEventButton.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, CreateEventActivity.class);
            startActivity(intent);
        });

        // Navigates to create event activity.
        mapButton = (Button) findViewById(R.id.mapButton);
        mapButton.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, MapsActivity.class);
            startActivity(intent);
        });

        // Go to profile
        Button profile = (Button) findViewById(R.id.userProfileButton);
        profile.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
            startActivity(intent);
        });
    }

    //this is listens to clicks on each row, (each public event displayed)
    // should open specific event info after click
    // position is index of the event in the list, could pass in more info maybe?
    @Override
    public void onItemClick(int position) {
        Log.d("---INDEX: " +  String.valueOf(position), "Clicked!!");
        Event e = myAdapter.getEvents().get(position);
        Intent intent = new Intent(HomeActivity.this, EventDetailsActivity.class);
        intent.putExtra("from", "home");
        intent.putExtra("eventID", e.getId().toString());
        startActivity(intent);
    }
}
