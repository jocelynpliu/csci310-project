package csci310.team53.easyteamup.data;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import csci310.team53.easyteamup.R;

import java.time.LocalDateTime;
import java.util.List;

import csci310.team53.easyteamup.activities.EventsActivity;
import csci310.team53.easyteamup.activities.HomeActivity;
import csci310.team53.easyteamup.activities.HostedEventsActivity;
import csci310.team53.easyteamup.activities.InboxActivity;

public class Event  extends AppCompatActivity {
    private String name;
    private String location;
    private String description;
    private LocalDateTime start;
    private LocalDateTime end;
    private String host;
    private String id;
    private boolean isPrivate;
    private List<String> invitees;

    private Button myEventsButton;
    private Button inboxButton;
    private Button myHostedEventsButton;
    private Button myHomeButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        setTitle("A single event ");


        //button stuff---------------------------------------
        //go to inbox
        inboxButton = (Button) findViewById(R.id.inboxButton);
        inboxButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Event.this, InboxActivity.class);
                startActivity(intent);
            }
        });

        //go to My hosted Events
        myHostedEventsButton = (Button) findViewById(R.id.hostedEventsButton);
        myHostedEventsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Event.this, HostedEventsActivity.class);
                startActivity(intent);
            }
        });


        //go to Home
        myHomeButton= (Button) findViewById(R.id.homeButton);
        myHomeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Event.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        //go to myEvents
        myEventsButton= (Button) findViewById(R.id.myEventsButton);
        myEventsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(Event.this, EventsActivity.class);
                startActivity(intent);
            }
        });


    }

    public void inviteUser(String username) {
        invitees.add(username);
    }

    public void uninviteUser(String username) {
        // IMPLEMENT
    }
}
