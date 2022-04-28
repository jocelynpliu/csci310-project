package csci310.team53.easyteamup.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.bson.Document;
import org.bson.types.ObjectId;

import csci310.team53.easyteamup.EasyTeamUp;
import csci310.team53.easyteamup.R;
import csci310.team53.easyteamup.activities.adapters.MessageRecyclerAdapter;
import csci310.team53.easyteamup.activities.adapters.RecyclerViewInterface;
import csci310.team53.easyteamup.data.Message;

/**
 * The inbox screen, displaying event invites and notifications.
 *
 * @author Justin Nakama, Thomas Peters
 */
public class InboxActivity extends AppCompatActivity implements RecyclerViewInterface {

    private EasyTeamUp app;
    private RecyclerView inboxRecyclerView;
    private MessageRecyclerAdapter myAdapter;

    private Button inboxButton;
    private Button myHomeButton;
    private Button myEventsButton;
    private Button myHostedEventsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        app = (EasyTeamUp) this.getApplication();
        Log.d("----------start", " InboxActivity.java");

        //gray out the button to make it look selected and cool
        setTitle("Inbox");
        inboxButton = (Button) findViewById(R.id.inboxButton);
        inboxButton.setAlpha(.5f);

        //recycler and adapter needed to display a dynamic list on the screen
        inboxRecyclerView = findViewById(R.id.inboxRecyclerView);
        inboxRecyclerView.setNestedScrollingEnabled(false);
        inboxRecyclerView.setHasFixedSize(true);

        // Retrieve messages from database and display from adapter.
        Document userQuery = new Document("_id", new ObjectId(app.getRealm().currentUser().getId()));
        app.getDatabase().users.find(userQuery).iterator().getAsync(task -> {
            Document query = new Document("_id", new Document("$in", task.get().next().getMessages()));
            app.getDatabase().messages.find(query).iterator().getAsync(task2 -> {
                myAdapter = new MessageRecyclerAdapter(app, this, task2.get(), this);
                inboxRecyclerView.setAdapter(myAdapter);
                inboxRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            });
        });

        //go to my hosted events
        myHostedEventsButton = (Button) findViewById(R.id.hostedEventsButton);
        myHostedEventsButton.setOnClickListener(v -> {
            Intent intent = new Intent(InboxActivity.this, HostedEventsActivity.class);
            startActivity(intent);
        });

        //go to My Events
        myEventsButton = (Button) findViewById(R.id.myEventsButton);
        myEventsButton.setOnClickListener(v -> {
            Intent intent = new Intent(InboxActivity.this, EventsActivity.class);
            startActivity(intent);
        });

        //go to Home
        myHomeButton= (Button) findViewById(R.id.homeButton);
        myHomeButton.setOnClickListener(v -> {
            Intent intent = new Intent(InboxActivity.this, HomeActivity.class);
            startActivity(intent);
        });
    }

    /**
     * Listens to clicks on each row, (each notification received displayed)
     * should open specific event info about notification after click
     * position is index of the notification in the list
     */
    @Override
    public void onItemClick(int position) {
        Log.d("---INDEX: " +  String.valueOf(position), "Clicked!!");
        Message msg = myAdapter.getMessages().get(position);
        if (msg.getEvent() != null) {
            Intent intent = new Intent(this, InviteActivity.class);
            intent.putExtra("content", msg.getContent());
            intent.putExtra("senderID", msg.getSender().toString());
            intent.putExtra("eventID", msg.getEvent().toString());
            startActivity(intent);
        } else {
            // TODO: Maybe have a message details activity for non-invite notifications?
        }
    }
}
