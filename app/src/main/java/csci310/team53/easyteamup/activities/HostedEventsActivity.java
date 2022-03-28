package csci310.team53.easyteamup.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import csci310.team53.easyteamup.EasyTeamUp;
import csci310.team53.easyteamup.R;

import csci310.team53.easyteamup.activities.adapters.HostedEventsRecyclerAdapter;
import csci310.team53.easyteamup.activities.adapters.RecyclerViewInterface;

/**
 * The hosted events screen, displaying events that this user is hosting.
 *
 * @author Thomas Peters, Justin Nakama
 */
public class HostedEventsActivity extends AppCompatActivity implements RecyclerViewInterface {

    private EasyTeamUp app;
    private RecyclerView myHostedEventsRecyclerView;

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
                HostedEventsRecyclerAdapter myAdapter = new HostedEventsRecyclerAdapter(app, this, task.get(), this);
                myHostedEventsRecyclerView.setAdapter(myAdapter);
                myHostedEventsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            } else {
                Log.v("Events", "ERROR: " + task.getError().getErrorMessage());
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
        Intent intent = new Intent( this, EventDetailsActivity.class);
        intent.putExtra("from", "hosted");

        startActivity(intent);
    }
}
