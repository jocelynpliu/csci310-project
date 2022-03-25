package csci310.team53.easyteamup.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import csci310.team53.easyteamup.R;
import csci310.team53.easyteamup.handlers.MessageHandler;

import androidx.appcompat.app.AppCompatActivity;


public class InviteDetailsActivity extends AppCompatActivity{

    private Button myEventsButton;
    private Button inboxButton;
    private Button myHostedEventsButton;
    private Button myHomeButton;
    private Button myAcceptButton;
    private Button myDenyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);

        setTitle("Event Invitation ");

        //button stuff---------------------------------------
        //go to inbox
        inboxButton = (Button) findViewById(R.id.inboxButton);
        inboxButton.setOnClickListener(v -> {
            Intent intent = new Intent(InviteDetailsActivity.this, InboxActivity.class);
            startActivity(intent);
        });

        //go to My hosted Events
        myHostedEventsButton = (Button) findViewById(R.id.hostedEventsButton);
        myHostedEventsButton.setOnClickListener(v -> {
            Intent intent = new Intent(InviteDetailsActivity.this, HostedEventsActivity.class);
            startActivity(intent);
        });


        //go to Home
        myHomeButton= (Button) findViewById(R.id.homeButton);
        myHomeButton.setOnClickListener(v -> {
            Intent intent = new Intent(InviteDetailsActivity.this, HomeActivity.class);
            startActivity(intent);
        });

        //go to myEvents
        myEventsButton= (Button) findViewById(R.id.myEventsButton);
        myEventsButton.setOnClickListener(v -> {
            Intent intent = new Intent(InviteDetailsActivity.this, EventsActivity.class);
            startActivity(intent);
        });



        // when you accept or deny, will pass extra info, return back to InboxActitity class to notify hosts
        myAcceptButton= (Button) findViewById(R.id.acceptButton);
        myAcceptButton.setOnClickListener(v -> {
            Intent intent = new Intent(InviteDetailsActivity.this, MessageHandler.class);
            intent.putExtra("attending", "yes");
            Log.d("here", "test");
            startActivity(intent);
        });


        myDenyButton= (Button) findViewById(R.id.denyButton);
        myDenyButton.setOnClickListener(v -> {
            Intent intent = new Intent(InviteDetailsActivity.this, MessageHandler.class);
            intent.putExtra("attending", "no");
            Log.d("here", "test");
            startActivity(intent);
        });


    }
}
