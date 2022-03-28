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
 * The hosted events screen, displaying events that this user is hosting.
 *
 * @author Thomas Peters, Justin Nakama
 */
public class HostedEventsActivity extends AppCompatActivity implements RecyclerViewInterface {

    private EasyTeamUp app;
    private RecyclerView myHostedEventsRecyclerView;
    private EventsRecyclerAdapter myAdapter;

    private Button myHostedEventsButton;
    private Button inboxButton;
    private Button myEventsButton;
    private Button myHomeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_hosted_events);
        app = ((EasyTeamUp) this.getApplication());

        //gray out the button to make it look selected and cool
        setTitle("My Hosted Events");
        myHostedEventsButton = (Button) findViewById(R.id.hostedEventsButton);
        myHostedEventsButton.setAlpha(.5f);

        //recycler and adapter needed to display a dynamic list on the screen
        myHostedEventsRecyclerView = findViewById(R.id.myHostedEventsRecyclerView);
        myHostedEventsRecyclerView.setNestedScrollingEnabled(false);
        myHostedEventsRecyclerView.setHasFixedSize(true);

        // Retrieve public events from database and display from adapter.
        app.getEventHandler().retrieveHostedEvents().getAsync(task -> {
            if (task.isSuccess()) {
                Log.v("Events", "Has Next: " + task.get().hasNext());
                myAdapter = new EventsRecyclerAdapter(app, this, task.get(), this);
                myHostedEventsRecyclerView.setAdapter(myAdapter);
                myHostedEventsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            } else {
                Log.v("Events", "ERROR: " + task.getError().getErrorMessage());
                Log.v("Events", "ERROR: " + task.getError().getException().getMessage());
            }
        });

        //go to inbox
        inboxButton = (Button) findViewById(R.id.inboxButton);
        inboxButton.setOnClickListener(v -> {
            Intent intent = new Intent(HostedEventsActivity.this, InboxActivity.class);
            startActivity(intent);
        });

        //go to My Events
        myEventsButton = (Button) findViewById(R.id.myEventsButton);
        myEventsButton.setOnClickListener(v -> {
            Intent intent = new Intent(HostedEventsActivity.this, EventsActivity.class);
            startActivity(intent);
        });

        //go to Home
        myHomeButton= (Button) findViewById(R.id.homeButton);
        myHomeButton.setOnClickListener(v -> {
            Intent intent = new Intent(HostedEventsActivity.this, HomeActivity.class);
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

        Intent intent = new Intent( HostedEventsActivity.this, EventDetailsActivity.class);
        intent.putExtra("from", "hosted");
        intent.putExtra("eventID", e.getId().toString());


        startActivity(intent);
    }
}
