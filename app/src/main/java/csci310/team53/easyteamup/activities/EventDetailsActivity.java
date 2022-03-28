package csci310.team53.easyteamup.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import csci310.team53.easyteamup.EasyTeamUp;
import csci310.team53.easyteamup.R;

public class EventDetailsActivity extends AppCompatActivity {

    private Button myEventsButton;
    private Button inboxButton;
    private Button myHostedEventsButton;
    private Button myHomeButton;

    private Button myjoinButton;
    private Button myLeaveButton;

    private EditText dateText;
    private EditText startTimeText;
    private EditText endTimeText;

    private Calendar calendar;
    private DatePickerDialog.OnDateSetListener date;
    private TimePickerDialog.OnTimeSetListener sTime;
    private TimePickerDialog.OnTimeSetListener eTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EasyTeamUp app = (EasyTeamUp) this.getApplication();



        //if you came from hosted, cameFrom will not be null
        String cameFrom = getIntent().getStringExtra("from");
        Log.d("cameFrom ", cameFrom + "!!!!");

        String eventID = getIntent().getStringExtra("eventID");
        Log.d("eventId ", eventID + "!!!!");

        setContentView(R.layout.activity_event);



            if (cameFrom.equals("hosted")) {
                setContentView(R.layout.activity_myeventdetails);
            }
            else if(cameFrom.equals("attending")){
                setContentView(R.layout.activity_attending_event);
            }



        setTitle("A single event ");

        //button stuff---------------------------------------
        //go to inbox
        inboxButton = (Button) findViewById(R.id.inboxButton);
        inboxButton.setOnClickListener(v -> {
            Intent intent = new Intent(EventDetailsActivity.this, InboxActivity.class);
            startActivity(intent);
        });

        //go to My hosted Events
        myHostedEventsButton = (Button) findViewById(R.id.hostedEventsButton);
        myHostedEventsButton.setOnClickListener(v -> {
            Intent intent = new Intent(EventDetailsActivity.this, HostedEventsActivity.class);
            startActivity(intent);
        });


        myjoinButton = (Button) findViewById(R.id.hostedEventsButton);
        myHostedEventsButton.setOnClickListener(v -> {
            Intent intent = new Intent(EventDetailsActivity.this, HostedEventsActivity.class);
            startActivity(intent);
        });


        //go to Home
        myHomeButton = (Button) findViewById(R.id.homeButton);
        myHomeButton.setOnClickListener(v -> {
            Intent intent = new Intent(EventDetailsActivity.this, HomeActivity.class);
            startActivity(intent);
        });

        //go to myEvents
        myEventsButton = (Button) findViewById(R.id.myEventsButton);
        myEventsButton.setOnClickListener(v -> {
            Intent intent = new Intent(EventDetailsActivity.this, EventsActivity.class);
            startActivity(intent);
        });

        // date/time pickers
        dateText = findViewById(R.id.dateText);
        startTimeText = findViewById(R.id.startTimeText);
        endTimeText = findViewById(R.id.endTimeText);

        calendar = Calendar.getInstance();

        date = (view, year, month, day) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, day);
            String format = "MM/dd/yy";
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
            dateText.setText(sdf.format(calendar.getTime()));
        };

        sTime = (view, hourOfDay, minute) -> {
            setTimePicker(view, hourOfDay, minute, startTimeText);
        };

        eTime = (view, hourOfDay, minute) -> {
            setTimePicker(view, hourOfDay, minute, endTimeText);
        };

        Log.d("camefrom", "camefrom is " + cameFrom);
//        Log.d("eventID!! ", eventID);

        if (cameFrom.equals("home") ) {
            myjoinButton = (Button) findViewById(R.id.joinEventButton);
            myjoinButton.setOnClickListener(v -> {
                // Add this user to list of attendees for specified event
                app.getEventHandler().attendEvent(eventID).getAsync(task -> {

                    if (task.isSuccess()) {

                        Log.d("eventID!! ", eventID);


                        Intent intent = new Intent(EventDetailsActivity.this, InviteResultActivity.class);
                        intent.putExtra("isAttending", true);
                        intent.putExtra("hostID", task.get().getHost().toString());
                        startActivity(intent);
                    }
                });
            });
        }

        if (cameFrom.equals("attending") ) {
            myLeaveButton = (Button) findViewById(R.id.leaveEventButton);
            myLeaveButton.setOnClickListener(v -> {
                // Add this user to list of attendees for specified event
                app.getEventHandler().attendEvent(eventID).getAsync(task -> {

                    if (task.isSuccess()) {

                        Log.d("eventID!! ", eventID);


                        Intent intent = new Intent(EventDetailsActivity.this, InviteResultActivity.class);
                        intent.putExtra("isAttending", false);
                        intent.putExtra("hostID", task.get().getHost().toString());
                        startActivity(intent);
                    }
                });
            });
        }




    }




    private void setTimePicker(View view, int hourOfDay, int minute, EditText text) {
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        String format = "hh:mm a";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        text.setText(sdf.format(calendar.getTime()));
    }

    public void datePickerClick(View view) {
        new DatePickerDialog(EventDetailsActivity.this, date, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void sTimePickerClick(View view) {
        new TimePickerDialog(EventDetailsActivity.this, sTime, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
    }

    public void eTimePickerClick(View view) {
        new TimePickerDialog(EventDetailsActivity.this, eTime, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();

    }

}