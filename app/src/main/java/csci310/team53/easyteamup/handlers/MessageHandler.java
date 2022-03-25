package csci310.team53.easyteamup.handlers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import csci310.team53.easyteamup.EasyTeamUp;
import csci310.team53.easyteamup.R;
import csci310.team53.easyteamup.activities.EventsActivity;
import csci310.team53.easyteamup.activities.HomeActivity;
import csci310.team53.easyteamup.activities.HostedEventsActivity;
import csci310.team53.easyteamup.activities.InboxActivity;
import csci310.team53.easyteamup.data.Message;

/**
 * Handles CRUD for Event objects in Database.
 *
 * @author Thomas Peters
 */
public class MessageHandler extends AppCompatActivity {

    private Button myEventsButton;
    private Button myHostedEventsButton;
    private Button myHomeButton;
    private Button inboxButton;

    private final EasyTeamUp app;

    /**
     * Constructor with reference to application class.
     *
     * @param app reference to main application class.
     */
    public MessageHandler(EasyTeamUp app) {
        this.app = app;
    }

    public MessageHandler(){
        app = null;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        Intent incomingIntent = getIntent();

        String extra = getIntent().getStringExtra("attending");
        Log.d("here", "test!!!!!!");

        Log.d("in messagehandler ", "user decided: " + extra);


//        final LayoutInflater factory = getLayoutInflater();
//
//        final View textEntryView = factory.inflate(R.layout.activity_confirm, null);


//        go to inbox
        inboxButton = (Button) findViewById(R.id.inboxButton);
        inboxButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MessageHandler.this, InboxActivity.class);
                startActivity(intent);
            }
        });

        //go to My hosted Events
        myHostedEventsButton = (Button) findViewById(R.id.hostedEventsButton);
        myHostedEventsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MessageHandler.this, HostedEventsActivity.class);
                startActivity(intent);
            }
        });

        // Navigate to My Events
        myEventsButton = (Button) findViewById(R.id.myEventsButton);
        myEventsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MessageHandler.this, EventsActivity.class);
                startActivity(intent);
            }
        });

        //go to Home
        myHomeButton= (Button) findViewById(R.id.homeButton);
        myHomeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MessageHandler.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }



    /**
     * Creates a new message and adds it to the receivers' profile.
     *
     * @param receivers list of user IDs of the users receiving this message.
     * @param content The content body of the message.
     */
    public void sendMessage(List<String> receivers, String content) {
        ObjectId id = new ObjectId();
        String sender = app.getRealm().currentUser().getId();
        Message message = new Message(id, sender, receivers, content);
        app.getDatabase().messages.insertOne(message).getAsync(task -> {
            if (task.isSuccess()) {
                for (String userID : receivers) {
                    Document findQuery = new Document("_id", new ObjectId(userID));
                    Document updateQuery = new Document("$push", new Document("messages", id));
                    app.getDatabase().users.findOneAndUpdate(findQuery, updateQuery).getAsync(task2 -> {
                        if (!task2.isSuccess()) {
                            Log.v("Message", "ERROR: " + task2.getError().getErrorMessage());
                        }
                    });
                }
            }
        });
    }

    /**
     * Removes all messages from the current user's inbox.
     * Will keep the messages themselves in the database, just disassociate them from this user.
     */
    public void clearInbox() {
        String userID = app.getRealm().currentUser().getId();
        Document findQuery = new Document("_id", new ObjectId(userID));
        Document updateQuery = new Document("$set", new Document("messages", new ArrayList<ObjectId>()));
        app.getDatabase().users.findOneAndUpdate(findQuery, updateQuery).getAsync(task -> {
            if (!task.isSuccess()) {
                Log.v("Message", "ERROR: " + task.getError().getErrorMessage());
            }
        });
    }
}
