package csci310.team53.easyteamup.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import csci310.team53.easyteamup.EasyTeamUp;
import csci310.team53.easyteamup.R;
import csci310.team53.easyteamup.activities.adapters.EventsRecyclerAdapter;
import csci310.team53.easyteamup.activities.adapters.RecyclerViewInterface;
import csci310.team53.easyteamup.data.Event;
import csci310.team53.easyteamup.util.TimeSlot;

/**
 * The events screen, displaying events that the user has marked as attending.
 *
 * @author Thomas Peters, Justin Nakama
 */
public class EventsActivity extends AppCompatActivity implements RecyclerViewInterface {

    private EasyTeamUp app;
    private RecyclerView eventsRecyclerView;
    private EventsRecyclerAdapter myAdapter;

    private Button myEventsButton;
    private Button myHostedEventsButton;
    private Button myHomeButton;
    private Button inboxButton;

    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> timeSlots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_events);

        app = ((EasyTeamUp) this.getApplication());

        setTitle("Attending Events");
        myEventsButton = (Button) findViewById(R.id.myEventsButton);
        myEventsButton.setAlpha(.5f);

        //recycler and adapter needed to display a dynamic list on the screen
        eventsRecyclerView = findViewById(R.id.myEventsRecyclerView);
        eventsRecyclerView.setNestedScrollingEnabled(false);
        eventsRecyclerView.setHasFixedSize(true);

        // Retrieve events this user is attending from database and display from adapter.
        app.getEventHandler().retrieveAttendingEvents().getAsync(task -> {
            if (task.isSuccess()) {
                myAdapter = new EventsRecyclerAdapter(app, this, task.get(), this);
                eventsRecyclerView.setAdapter(myAdapter);
                eventsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            } else {
                Log.v("Events", "ERROR: " + task.getError().getErrorMessage());
            }
        });

        //go to inbox
        inboxButton = (Button) findViewById(R.id.inboxButton);
        inboxButton.setOnClickListener(v -> {
            Intent intent = new Intent(EventsActivity.this, InboxActivity.class);
            startActivity(intent);
        });

        //go to My hosted Events
        myHostedEventsButton = (Button) findViewById(R.id.hostedEventsButton);
        myHostedEventsButton.setOnClickListener(v -> {
            Intent intent = new Intent(EventsActivity.this, HostedEventsActivity.class);
            startActivity(intent);
        });

        //go to Home
        myHomeButton= (Button) findViewById(R.id.homeButton);
        myHomeButton.setOnClickListener(v -> {
            Intent intent = new Intent(EventsActivity.this, HomeActivity.class);
            startActivity(intent);
        });
    }

    //this is listens to clicks on each row, (each notification received displayed)
    // should open specific event info about notification after click
    // position is index of the notification in the list
    @Override
    public void onItemClick(int position) {
        Log.d("---INDEX: " +  String.valueOf(position), "Clicked!!");

        Event e = myAdapter.getEvents().get(position);

        Log.d("click eventsactivity ", e.getName() + "!!!!!!!!!!");

        Intent intent = new Intent(EventsActivity.this, EventDetailsActivity.class);
        intent.putExtra("eventID", e.getId().toString());
        intent.putExtra("from", "attending");

        startActivity(intent);
    }
}
